package dev.vaccinelocator.respository.telegram;

import org.springframework.web.reactive.function.client.WebClient;

public interface TelegramRepository {

  WebClient.ResponseSpec postMessageToChannel(String message);
}
