package dev.nk7.demon.client.rest;

import dev.nk7.demon.api.v1.DemonApi;
import okhttp3.Response;

public class BadResponseCodeException extends DemonApi.Exception {
  public BadResponseCodeException(Response response) {
    super(String.format("Получен ошибочный код ответа: '%s'.", response.toString()));
  }
}
