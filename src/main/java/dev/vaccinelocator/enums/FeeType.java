package dev.vaccinelocator.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum FeeType {
  FREE("Free"),
  PAID("Paid"),
  BLANK("");

  private String value;
  FeeType(String value) {
    this.value = value;
  }
  @JsonValue
  public String getValue() { return this.value; }
}
