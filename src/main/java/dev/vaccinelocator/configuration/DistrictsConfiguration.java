package dev.vaccinelocator.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
@ConfigurationProperties(prefix = "districts")
public class DistrictsConfiguration {

  private Map<String, String> all;

  public Map<String, String> getAll() {
    return all;
  }

  public void setAll(Map<String, String> all) {
    this.all = all;
  }
}
