package dev.vaccinelocator.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum VaccineBrand {
  COVISHIELD("COVISHIELD"),
  COVAXIN("COVAXIN"),
  BLANK("");

  private String value;
  VaccineBrand(String value) {
    this.value = value;
  }
  @JsonValue
  public String getValue() { return this.value; }

}
