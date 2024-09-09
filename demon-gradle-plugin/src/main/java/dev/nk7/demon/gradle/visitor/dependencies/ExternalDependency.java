package dev.nk7.demon.gradle.visitor.dependencies;


import dev.nk7.demon.api.v1.dto.DependencyDto;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

class ExternalDependency implements AbstractDependency {

  private final String group;
  private final String artifact;
  private final String version;
  private final Set<AbstractDependency> dependsOn;

  public ExternalDependency(String group, String artifact, String version, Set<AbstractDependency> dependsOn) {
    this.group = Objects.requireNonNull(group);
    this.artifact = Objects.requireNonNull(artifact);
    this.version = Objects.requireNonNull(version);
    this.dependsOn = Objects.requireNonNull(dependsOn);
  }

  @Override
  public void flush(ModuleDependencies moduleDependencies, String configurationName) {
    final Set<DependencyDto> dependencies = dependsOn.stream().map(this::mapTree).collect(Collectors.toSet());
    final DependencyDto dependency = new DependencyDto(group, artifact, version, dependencies);
    final Set<DependencyDto> set = moduleDependencies.getExternalDependencies().computeIfAbsent(configurationName, key -> new HashSet<>());
    set.add(dependency);
  }

  private DependencyDto mapTree(AbstractDependency abstractDependency) {
    if (abstractDependency instanceof ExternalDependency) {
      final ExternalDependency dependency = (ExternalDependency) abstractDependency;
      final Set<DependencyDto> dependsOn = dependency.dependsOn.stream()
        .map(this::mapTree)
        .collect(Collectors.toSet());
      return new DependencyDto(dependency.group, dependency.artifact, dependency.version, dependsOn);
    } else {
      throw new IllegalStateException("ExternalDependency can depends only ExternalDependency");
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final ExternalDependency that = (ExternalDependency) o;
    return Objects.equals(group, that.group) && Objects.equals(artifact, that.artifact) && Objects.equals(version, that.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(group, artifact, version);
  }
}
