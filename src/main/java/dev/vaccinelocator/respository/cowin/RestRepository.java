package dev.vaccinelocator.respository.cowin;

import org.springframework.web.reactive.function.client.WebClient;

public interface RestRepository<T> {

  WebClient.ResponseSpec findAll();
}
