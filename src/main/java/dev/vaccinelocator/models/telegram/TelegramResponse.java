package dev.vaccinelocator.models.telegram;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TelegramResponse {
  private String ok;
  private Result result;
}
