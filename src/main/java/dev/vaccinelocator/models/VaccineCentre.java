package dev.vaccinelocator.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import dev.vaccinelocator.enums.FeeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VaccineCentre {
  private int center_id;
  private String name;
  private String address;
  private String state_name;
  private String district_name;
  private String block_name;
  private int pincode;
  private int lat;
  @JsonProperty("long")
  private int longitude;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
  private Date from;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
  private Date to;
  private FeeType fee_type;
  private List<Session> sessions;

}
