package dev.vaccinelocator.respository.telegram;

import dev.vaccinelocator.CowinException;
import dev.vaccinelocator.configuration.VaccineLocatorConfiguration;
import dev.vaccinelocator.constants.RequestConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class TelegramRepositoryImpl implements TelegramRepository {

  private static final String BOT_URI = "/bot";
  private static final String SEND_MESSAGE = "/sendMessage?";
  private static final String CHAT_ID_PARAM = "chat_id=";
  private static final String MESSAGE_PARAM = "text";
  private static final String AMPERSAND = "&";

  private final WebClient telegramWebClient;
  private final VaccineLocatorConfiguration configuration;

  @Autowired
  public TelegramRepositoryImpl(WebClient telegramWebClient, VaccineLocatorConfiguration vaccineLocatorConfiguration){
    this.telegramWebClient = telegramWebClient;
    this.configuration = vaccineLocatorConfiguration;

  }

  @Override
  public WebClient.ResponseSpec postMessageToChannel(String message) {
    return telegramWebClient
        .get()
        .uri(getTelegram(message))
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .header(HttpHeaders.ACCEPT_LANGUAGE,RequestConstants.LANGUAGE)
        .header(HttpHeaders.USER_AGENT, RequestConstants.USER_AGENT)
        .retrieve()
        .onStatus(HttpStatus::is5xxServerError, response -> Mono.just(new CowinException("500 error!")))
        .onStatus(HttpStatus::is4xxClientError, response -> Mono.just(new CowinException("400 error!")));
  }

  private String getTelegram(String message) {
    return BOT_URI + configuration.getTApiKey() +
        SEND_MESSAGE + CHAT_ID_PARAM + "@" +configuration.getTChannelName() +
        AMPERSAND +
        MESSAGE_PARAM + "=" + message;

  }
}
