package dev.vaccinelocator.models.telegram;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SenderChat {
  private String id;
  private String title;
  private String username;
  private String type;
}
