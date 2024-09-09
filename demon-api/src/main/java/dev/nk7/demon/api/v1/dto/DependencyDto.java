package dev.nk7.demon.api.v1.dto;

import java.util.Objects;
import java.util.Set;

public class DependencyDto {
  private final String groupId;
  private final String artifactId;
  private final String version;
  private final Set<DependencyDto> dependsOn;

  public DependencyDto(String groupId, String artifactId, String version, Set<DependencyDto> dependsOn) {
    this.groupId = Objects.requireNonNull(groupId);
    this.artifactId = Objects.requireNonNull(artifactId);
    this.version = Objects.requireNonNull(version);
    this.dependsOn = Objects.requireNonNull(dependsOn);
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

  public Set<DependencyDto> getDependsOn() {
    return dependsOn;
  }
}
