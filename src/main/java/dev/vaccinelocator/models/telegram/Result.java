package dev.vaccinelocator.models.telegram;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
  private int message_id;
  private SenderChat sender_chat;
  private Chat chat;
  private String date;
  private String text;
}
