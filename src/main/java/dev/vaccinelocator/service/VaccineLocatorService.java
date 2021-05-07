package dev.vaccinelocator.service;

import dev.vaccinelocator.models.DistrictCentres;
import dev.vaccinelocator.models.VaccineCentre;
import reactor.core.publisher.Flux;

import java.util.List;

public interface VaccineLocatorService {

  Flux<DistrictCentres> getAvailableVaccines();

  Flux<List<VaccineCentre>> getEighteenPlusCentres();
}
