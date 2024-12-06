package dev.nk7.demon.api.v1.dto;

import java.util.Objects;
import java.util.Set;

/**
 * External dependency.
 */
public class DependencyDto {
  /**
   * Group id.
   */
  private final String groupId;
  /**
   * Artifact id.
   */
  private final String artifactId;
  /**
   * Version.
   */
  private final String version;
  /**
   * Set of transitive dependencies.
   */
  private final Set<DependencyDto> dependsOn;

  /**
   * Constructor for DependencyDto.
   *
   * @param groupId    Group id.
   * @param artifactId Artifact id.
   * @param version    Version.
   * @param dependsOn  Set of transitive dependencies.
   */
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
