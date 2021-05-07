package dev.vaccinelocator.scheduling;

import dev.vaccinelocator.models.VaccineCentre;
import dev.vaccinelocator.service.TelegramService;
import dev.vaccinelocator.service.VaccineLocatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableAsync
@Slf4j
public class Scheduler {
  private static final int ONE_MINUTE = 1000 * 60;
  private static final int FIXED_SCHEDULER_DELAY = 10 * ONE_MINUTE;

  private final VaccineLocatorService vaccineLocatorService;
  private final TelegramService telegramService;

  @Autowired
  public Scheduler(final VaccineLocatorService vaccineLocatorService, final TelegramService telegramService) {
    this.vaccineLocatorService = vaccineLocatorService;
    this.telegramService = telegramService;
  }

  @Scheduled(fixedDelay = (FIXED_SCHEDULER_DELAY))
  public void testSchedule() {
    log.info("Schedular invoked");
    vaccineLocatorService.getEighteenPlusCentres()
        .subscribe(vaccineCentres -> {
          telegramService.postMessageToChannel(vaccineCentres);
        });
  }

  private void logVaccineCentres(List<VaccineCentre> vaccineCentres) {
    vaccineCentres.forEach(centres -> {
          log.info("Following is the centres with Available Vaccines");
          System.out.println("+++++++++++Centre Info START+++++++++++");
          System.out.println("Vaccine Centre ID " + centres.getCenter_id());
          System.out.println("Vaccine Centre Name " + centres.getName());
          System.out.println("Vaccine Centre Session Info");
          centres.getSessions().forEach(session -> {
            System.out.println("!!!!!!!!!!!!!Session Info Start!!!!!!!!!!!!!");
            System.out.println("Session Available Capacity : " + session.getAvailable_capacity());
            System.out.println("Session Minimum Age Criteria : " + session.getMin_age_limit());
            System.out.println(">>>>>>>>>>Session Slots START>>>>>>>>>>");
            session.getSlots().forEach(slot -> {
              System.out.println("AVAILABLE TIME SLOT : " + slot);
            });
            System.out.println(">>>>>>>>>>Session Slots END>>>>>>>>>>");
            System.out.println("!!!!!!!!!!!!!Session Info END!!!!!!!!!!!!!");
          });
          System.out.println("+++++++++++Centre Info END+++++++++++");
        }
    );


  }
}
