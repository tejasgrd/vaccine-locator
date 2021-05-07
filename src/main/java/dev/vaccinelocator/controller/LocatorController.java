package dev.vaccinelocator.controller;

import dev.vaccinelocator.models.VaccineCentre;
import dev.vaccinelocator.service.VaccineLocatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("v1/centres")
public class LocatorController {

  @Autowired
  VaccineLocatorService service;

  @GetMapping
  public Flux<List<VaccineCentre>> test(){
    return service.getEighteenPlusCentres();
  }

}
