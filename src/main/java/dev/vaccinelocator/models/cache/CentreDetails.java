package dev.vaccinelocator.models.cache;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CentreDetails {

  private int centreId;
  private String centreName;
  private int totalCapacity;
}
