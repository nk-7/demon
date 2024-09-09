package dev.nk7.demon.gradle.visitor.dependencies;

import org.gradle.api.artifacts.Configuration;
import org.gradle.api.internal.artifacts.configurations.ConfigurationInternal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

class ConfigurationFilter {

  private static final Logger log = LoggerFactory.getLogger(ConfigurationFilter.class);

  List<ConfigurationInternal> filter(Iterable<Configuration> configurations) {
    final List<ConfigurationInternal> filtered = new ArrayList<>();
    for (final Configuration c : configurations) {
      if (!(c instanceof ConfigurationInternal)) {
        log.debug("Конфигурация '{}' не является ConfigurationInternal - пропускаем.",
          c.getName());
      } else if (!((ConfigurationInternal) c).isDeclarableByExtension()) {
        log.debug("Конфигурация '{}' не DeclarableByExtension - пропускаем.",
          c.getName());
      } else {
        filtered.add((ConfigurationInternal) c);
      }
    }
    return filtered;
  }
}
