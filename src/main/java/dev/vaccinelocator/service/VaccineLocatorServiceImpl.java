package dev.vaccinelocator.service;

import dev.vaccinelocator.models.DistrictCentres;
import dev.vaccinelocator.models.Session;
import dev.vaccinelocator.models.VaccineCentre;
import dev.vaccinelocator.respository.cowin.CowinCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VaccineLocatorServiceImpl implements VaccineLocatorService {

  private static final int AGE_EIGHTEEN_PLUS = 18;
  private final CowinCrudRepository cowinCrudRepository;


  @Autowired
  public VaccineLocatorServiceImpl(CowinCrudRepository cowinCrudRepository) {
    this.cowinCrudRepository = cowinCrudRepository;
  }

  @Override
  public Flux<DistrictCentres> getAvailableVaccines() {
    return cowinCrudRepository.getDistrictCentresByDistrictId();
  }

  @Override
  public Flux<List<VaccineCentre>> getEighteenPlusCentres() {
    return cowinCrudRepository.getDistrictCentresByDistrictId()
        .map(districtCentres -> districtCentres.getCenters())
        .map(vaccineCentres -> filterVaccineCentres(vaccineCentres));
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
    return session.getMin_age_limit() == AGE_EIGHTEEN_PLUS && session.getAvailable_capacity() >= 0;
  }
}
