package dev.nk7.demon.client.rest;

import dev.nk7.demon.api.v1.DemonApi;

/**
 * Demon backed client exception.
 */
public class ClientException extends DemonApi.Exception {
  public ClientException(String message, Throwable throwable) {
    super(message, throwable);
  }
}
