package dev.nk7.demon.client.rest;

import java.net.URI;
import java.util.Objects;

class DemonApiUris {

  final URI buildUri;

  DemonApiUris(String apiBaseUri) {
    Objects.requireNonNull(apiBaseUri);
    this.buildUri = URI.create(apiBaseUri + "/build");
  }

}
