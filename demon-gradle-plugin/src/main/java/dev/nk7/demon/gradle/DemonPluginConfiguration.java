package dev.nk7.demon.gradle;

import org.gradle.api.provider.Property;

import java.net.URI;

public abstract class DemonPluginConfiguration {

  /**
   * Base HTTP URI to send dependencies report.
   * Default value  is "http://localhost:8080".
   *
   * @return
   */
  abstract public Property<URI> getBackendBaseUrl();

}
