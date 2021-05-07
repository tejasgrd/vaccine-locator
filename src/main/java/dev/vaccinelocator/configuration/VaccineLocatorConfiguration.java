package dev.vaccinelocator.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Data
@AllArgsConstructor
@NoArgsConstructor
@EnableScheduling
public class VaccineLocatorConfiguration {

  @Value("${cowin.baseUrl}")
  private String cowinBaseUrl;
  @Value("${cowin.districtURI}")
  private String districtURI;
  @Value("${cowin.districtParam}")
  private String districtParam;
  @Value("${cowin.date}")
  private String date;
  @Value("${cowin.selectedDistrict}")
  private String selectedDistrict;

  @Value("${telegram.tBaseUrl}")
  private String tBaseUrl;
  @Value("${telegram.tApiKey}")
  private String tApiKey;

  @Value("${telegram.tChannelName}")
  private String tChannelName;

  @Bean("cowinWebClient")
  public WebClient getCowinWebClient() {
    return WebClient.builder()
        .baseUrl(cowinBaseUrl)
        .build();
  }

  @Bean("telegramWebClient")
  public WebClient getTelegramWebClient(){
    return WebClient.builder()
        .baseUrl(tBaseUrl)
        .build();
  }
}
