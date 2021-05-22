package dev.vaccinelocator.models.slotbooking;

import java.util.List;

public class SlotBookingRequest {
  private int center_id;
  private String session_id;
  private List<String> beneficiaries;
  private String slot;
  private String captcha;
  private int dose;
}
