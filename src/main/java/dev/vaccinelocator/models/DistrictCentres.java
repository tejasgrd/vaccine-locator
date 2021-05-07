package dev.vaccinelocator.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DistrictCentres {
  List<VaccineCentre> centers;
}
