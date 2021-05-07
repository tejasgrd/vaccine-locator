package dev.vaccinelocator.service;

import dev.vaccinelocator.models.VaccineCentre;

import java.util.List;

public interface TelegramService {

  void postMessageToChannel(List<VaccineCentre> vaccineCentres);
}
