package dev.vaccinelocator.respository.cowin;

import dev.vaccinelocator.configuration.DistrictsConfiguration;
import dev.vaccinelocator.configuration.VaccineLocatorConfiguration;
import dev.vaccinelocator.models.DistrictCentres;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
public class CowinRestRepositoryImpl extends AbstractRestRepository<DistrictCentres> implements CowinCrudRepository{

  @Autowired
  public CowinRestRepositoryImpl(WebClient cowinWebClient, VaccineLocatorConfiguration configuration,
                                 DistrictsConfiguration districtsConfiguration) {
    super(cowinWebClient, configuration, districtsConfiguration);
  }

  @Override
  public Flux<DistrictCentres> getDistrictCentresByDistrictId() {
    return findAll().bodyToFlux(DistrictCentres.class);
  }
}
