package dev.vaccinelocator.service;

import dev.vaccinelocator.models.Session;
import dev.vaccinelocator.models.VaccineCentre;
import dev.vaccinelocator.models.telegram.TelegramResponse;
import dev.vaccinelocator.respository.telegram.TelegramRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
                .subscribe(telegramResponse -> {
                    log.info(telegramResponse.getOk());
                })
        )
        .collect(Collectors.toList());
  }


  private String getAlertMessage(VaccineCentre centre){
    StringBuilder builder = new StringBuilder();
    builder.append("Centre ID : "+ centre.getCenter_id());
    builder.append("\n");
    builder.append("Centre Name : "+centre.getName());
    builder.append("\n");
    builder.append("Centre Address : "+centre.getAddress());
    builder.append("\n");
    builder.append("Available Sessions");
    builder.append("\n");
    for(Session session : centre.getSessions()){
      builder.append("Sessions :");
      builder.append("\n");
      builder.append("Date : "+session.getDateForVaccination());
      builder.append("\n");
      builder.append("Vaccine Type : "+session.getVaccine());
      builder.append("\n");
      builder.append("Available Quantity : "+session.getAvailable_capacity());
      builder.append("\n");
      builder.append("Minimum Age : "+session.getMin_age_limit());
      builder.append("\n");
      builder.append("Slots :");
      builder.append("\n");
      for(String slot: session.getSlots()){
        builder.append(slot);
        builder.append("\n");
      }
    }

    return builder.toString();
  }
}
