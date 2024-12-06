package dev.nk7.demon.api.v1.dto;

import java.time.Instant;
import java.util.Objects;
import java.util.Set;

/**
 * Report about project dependencies.
 */
public class DependenciesReportDto {
  /**
   * Report creation timestamp.
   */
  private final Instant timestamp;
  /**
   * Project name.
   */
  private final String name;
  /**
   * Git branch name.
   */
  private final String branch;
  /**
   * Direct project dependencies.
   */
  private final Set<DependencyDto> dependencies;
  /**
   * Set of project modules.
   */
  private final Set<ModuleDto> modules;

  public DependenciesReportDto(Instant timestamp, String name, String branch, Set<DependencyDto> dependencies, Set<ModuleDto> modules) {
    this.timestamp = Objects.requireNonNull(timestamp);
    this.name = Objects.requireNonNull(name);
    this.branch = branch;
    this.dependencies = dependencies;
    this.modules = modules;
  }

  public Instant getTimestamp() {
    return timestamp;
  }

  public String getName() {
    return name;
  }

  public String getBranch() {
    return branch;
  }

  public Set<DependencyDto> getDependencies() {
    return dependencies;
  }

  public Set<ModuleDto> getModules() {
    return modules;
  }
}
