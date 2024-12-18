package dev.nk7.demon.client.rest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

/**
 * Demon backend API URI provider.
 */
class DemonApiUrls {

  final URL buildUrl;

  DemonApiUrls(String apiBaseUri) throws MalformedURLException {
    Objects.requireNonNull(apiBaseUri);
    this.buildUrl = new URL(apiBaseUri + "/build");
  }

}
