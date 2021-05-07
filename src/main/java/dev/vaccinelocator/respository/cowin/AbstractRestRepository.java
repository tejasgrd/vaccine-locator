package dev.vaccinelocator.respository.cowin;

import dev.vaccinelocator.configuration.DistrictsConfiguration;
import dev.vaccinelocator.configuration.VaccineLocatorConfiguration;
import dev.vaccinelocator.constants.RequestConstants;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AbstractRestRepository<T> implements RestRepository<T> {

  private final WebClient webClient;
  private final VaccineLocatorConfiguration configuration;
  private final  DistrictsConfiguration districtsConfiguration;
  private static final String AMPERSAND = "&";
  private static final String EQUALS = "=";


  public AbstractRestRepository(final WebClient webClient, final VaccineLocatorConfiguration configuration,
                                final DistrictsConfiguration districtsConfiguration){
    this.webClient = webClient;
    this.configuration = configuration;
    this.districtsConfiguration = districtsConfiguration;
  }
  @Override
  public WebClient.ResponseSpec findAll() {
    return webClient
        .get()
        .uri(getUri())
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .header(HttpHeaders.ACCEPT_LANGUAGE,RequestConstants.LANGUAGE)
        .header(HttpHeaders.USER_AGENT, RequestConstants.USER_AGENT)
        .retrieve();
  }

  private String getUri() {
    return configuration.getDistrictURI() +
        configuration.getDistrictParam() + EQUALS + getDistrictId() +
        AMPERSAND +
        configuration.getDate() + EQUALS + getTodaysDateAsString();
  }


  private String getTodaysDateAsString(){
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat(RequestConstants.DATE_FORMAT_NOW);
    return sdf.format(cal.getTime());
  }

  private String getDistrictId(){
    return districtsConfiguration.getAll().get(configuration.getSelectedDistrict());
  }

}
