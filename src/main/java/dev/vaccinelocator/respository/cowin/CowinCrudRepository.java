package dev.vaccinelocator.respository.cowin;

import dev.vaccinelocator.models.DistrictCentres;
import reactor.core.publisher.Flux;

public interface CowinCrudRepository {

  Flux<DistrictCentres> getDistrictCentresByDistrictId();
}
