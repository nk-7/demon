package dev.nk7.demon.api.v1.dto;

import java.time.Instant;
import java.util.Objects;
import java.util.Set;

public class ProjectReportDto {
  /**
   * Время создания этого отчета.
   */
  private final Instant timestamp;
  /**
   * Имя проекта.
   */
  private final String name;
  /**
   * Имя ветки.
   */
  private final String branch;
  /**
   * Прямые зависимости проекта.
   * Родительский проект может сам содержать зависимости, не только модули.
   */
  private final Set<DependencyDto> dependencies;
  /**
   * Модули, из которых состоит проект.
   */
  private final Set<ModuleDto> modules;

  public ProjectReportDto(Instant timestamp, String name, String branch, Set<DependencyDto> dependencies, Set<ModuleDto> modules) {
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
