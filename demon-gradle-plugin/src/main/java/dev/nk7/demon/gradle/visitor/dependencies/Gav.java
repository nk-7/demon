package dev.nk7.demon.gradle.visitor.dependencies;

import org.gradle.api.artifacts.component.ComponentIdentifier;
import org.gradle.api.artifacts.component.ModuleComponentIdentifier;
import org.gradle.api.internal.artifacts.DefaultProjectComponentIdentifier;

import java.util.Optional;

class Gav {
  private final String groupId;
  private final String artifactId;
  private final String version;

  private Gav(String groupId, String artifactId, String version) {
    this.groupId = groupId;
    this.artifactId = artifactId;
    this.version = version;
  }

  static Optional<Gav> of(ComponentIdentifier id) {
    if (id instanceof ModuleComponentIdentifier) {
      final ModuleComponentIdentifier moduleComponentIdentifier = (ModuleComponentIdentifier) id;
      return Optional.of(new Gav(
        moduleComponentIdentifier.getGroup(),
        moduleComponentIdentifier.getModule(),
        moduleComponentIdentifier.getVersion()
      ));
    } else if (id instanceof DefaultProjectComponentIdentifier) {
      return Optional.empty();
    } else {
      throw new IllegalArgumentException(String.format("Анализа зависимостей типа '%s' пока не поддерживается.", id.getClass().getName()));
    }
  }

  public String getGroupId() {
    return groupId;
  }

  public String getArtifactId() {
    return artifactId;
  }

  public String getVersion() {
    return version;
  }
}
