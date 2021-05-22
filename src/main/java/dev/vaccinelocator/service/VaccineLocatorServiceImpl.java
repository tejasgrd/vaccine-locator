package dev.vaccinelocator.service;

import dev.vaccinelocator.models.DistrictCentres;
import dev.vaccinelocator.models.Session;
import dev.vaccinelocator.models.VaccineCentre;
import dev.vaccinelocator.models.cache.CentreDetails;
import dev.vaccinelocator.respository.cowin.CowinCrudRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VaccineLocatorServiceImpl implements VaccineLocatorService {

  private static final int AGE_EIGHTEEN_PLUS = 18;
  private final CowinCrudRepository cowinCrudRepository;
  private final TelegramService telegramService;
  private Map<Integer, CentreDetails> notifiedCentres = new ConcurrentHashMap<Integer, CentreDetails>();


  @Autowired
  public VaccineLocatorServiceImpl(final CowinCrudRepository cowinCrudRepository,
                                   final TelegramService telegramService) {
    this.cowinCrudRepository = cowinCrudRepository;
    this.telegramService = telegramService;
  }

  @Override
  public Flux<DistrictCentres> getAvailableVaccines() {
    return cowinCrudRepository.getDistrictCentresByDistrictId();
  }

  @Override
  public Flux<List<VaccineCentre>> getEighteenPlusCentres() {
    return cowinCrudRepository.getDistrictCentresByDistrictId()
        .map(districtCentres -> districtCentres.getCenters())
        .map(vaccineCentres -> removeAlreadyNotifiedCenters(filterVaccineCentres(vaccineCentres)));
  }

  private List<VaccineCentre> removeAlreadyNotifiedCenters(List<VaccineCentre> centres) {
    List<VaccineCentre> filteredCentres = centres.stream()
        .filter(this::isCentreUpdated)
        .collect(Collectors.toList());
    if (centres.isEmpty() && !notifiedCentres.isEmpty()) {
        log.info("No Slots found updating cache and posting message to Channel");
        telegramService.postUpdateMessage(new ArrayList<>(notifiedCentres.values()));
        notifiedCentres.clear();
    } else if (!filteredCentres.isEmpty()){
      log.info("New Slots found, updating cache");
      for (VaccineCentre filtered : filteredCentres) {
        int totalAvailabelVaccines = totalCapacityOfCentre(filtered);
        notifiedCentres.put(filtered.getCenter_id(), new CentreDetails(filtered.getCenter_id(), totalAvailabelVaccines));
      }
    }
    return filteredCentres;
  }

  private boolean isCentreUpdated(VaccineCentre vaccineCentre) {
    if (notifiedCentres.containsKey(accineCentre.getCenter_id())) {
      CentreDetails centreDetails = notifiedCentres.get(accineCentre.getCenter_id());
      return centreDetails.getTotalCapacity() != totalCapacityOfCentre(vaccineCentre) ? true :false;
    }
    return true
  }

  private int totalCapacityOfCentre(VaccineCentre vaccineCentre) {
    return vaccineCentre.getSessions().stream()
        .map(session -> session.getAvailable_capacity())
        .reduce(0, (a, b) -> a + b);
  }

  private List<VaccineCentre> filterVaccineCentres(List<VaccineCentre> centres) {
    return centres.stream()
        .filter(this::vaccineAvailabilityFilter)
        .collect(Collectors.toList());
  }

  private boolean filterAvailableSessionForEighteenPlus(List<Session> sessions) {
    return sessions.stream()
        .filter(VaccineLocatorServiceImpl::sessionFilter)
        .findAny().isPresent();
  }

  private boolean vaccineAvailabilityFilter(VaccineCentre centre) {
    return filterAvailableSessionForEighteenPlus(centre.getSessions());
  }
  /*
     Even if the single criteria met, that centre is filtered
   */
  private static boolean sessionFilter(Session session) {
    return session.getMin_age_limit() == AGE_EIGHTEEN_PLUS && session.getAvailable_capacity() > 0;
  }
}
