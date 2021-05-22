package dev.vaccinelocator.service;

import dev.vaccinelocator.CowinException;
import dev.vaccinelocator.models.Fees;
import dev.vaccinelocator.models.Session;
import dev.vaccinelocator.models.VaccineCentre;
import dev.vaccinelocator.models.telegram.TelegramResponse;
import dev.vaccinelocator.respository.telegram.TelegramRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TelegramServiceImpl implements TelegramService{
  private final TelegramRepository telegramRepository;

  @Autowired
  public TelegramServiceImpl(final TelegramRepository telegramRepository){
    this.telegramRepository = telegramRepository;
  }

  @Override
  public void postMessageToChannel(List<VaccineCentre> vaccineCentres) {
    vaccineCentres.stream()
        .map(vaccineCentre ->
            telegramRepository
                .postMessageToChannel(getAlertMessage(vaccineCentre))
                .bodyToMono(TelegramResponse.class)
                .retryWhen(
                    Retry.backoff(3, Duration.ofSeconds(3))// Max 3 retires with BackOff Strategy
                    .filter(telegramResponse -> telegramResponse instanceof CowinException)
                    .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> {
                        throw new CowinException("RetryExhausted Posting message to telegram");
                    })
                )

                .subscribe(telegramResponse -> {
      log.info(telegramResponse.getOk());
    })
        )
        .collect(Collectors.toList());
  }

  @Override
  public void postUpdateMessage(List<String> centres){
    telegramRepository
        .postMessageToChannel(getUpdateMessageStringForCentres(centres))
        .bodyToMono(TelegramResponse.class)
        .subscribe(telegramResponse -> {
          log.info("Updated empty status of vaccine slots to channel");
        });
  }

  private String getUpdateMessageStringForCentres(List<String> centres){
    StringBuilder builder = new StringBuilder();
    builder.append("Last updated vaccine slots are no longer available for below centres");
    builder.append("\n");
    for(String centre: centres){
      builder.append(centre);
      builder.append("\n");
    }
    return builder.toString();
  }


  private String getAlertMessage(VaccineCentre centre){
    StringBuilder builder = new StringBuilder();
    builder.append("Vaccination Centre");
    builder.append("\n");
    builder.append("\n");
    builder.append("Name : "+centre.getName());
    builder.append("\n");
    builder.append("\n");
    builder.append("Address : "+centre.getAddress());
    builder.append("\n");
    builder.append("\n");
    builder.append("Pincode : "+centre.getPincode());
    builder.append("\n");
    builder.append("Fee : "+centre.getFee_type().getValue());
    builder.append("\n");
    builder.append("Available Sessions");
    builder.append("\n");
    for(Session session : centre.getSessions()){
      builder.append("\n");
      builder.append("Date : "+session.getDateForVaccination());
      builder.append("\n");
      builder.append("Minimum Age : "+session.getMin_age_limit());
      builder.append("\n");
      builder.append("Vaccine Type : "+session.getVaccine());
      builder.append("\n");
      builder.append("Dose 1 Capacity : "+session.getAvailable_capacity_dose1());
      builder.append("\n");
      builder.append("Dose 2 Capacity : "+session.getAvailable_capacity_dose2());
      builder.append("\n");
    }
    if(centre.getVaccine_fees() != null) {
      for (Fees fee : centre.getVaccine_fees()) {
        builder.append("\n");
        builder.append("Fees : ₹" + fee.getFee());
      }
    }

    return builder.toString();
  }
}
