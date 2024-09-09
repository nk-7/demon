package dev.nk7.demon.api.v1.dto;

import java.util.Objects;
import java.util.Set;

public class ModuleDto {
  /**
   * Имя модуля.
   */
  private final String name;
  /**
   * Внешние зависимости.
   */
  private final Set<DependencyDto> dependencies;

  /**
   * Зависимость на модуль в рамках одного проекта. Тут указываются просто имена модулей.
   */
  private final Set<String> moduleDependencies;

  public ModuleDto(String name, Set<DependencyDto> dependencies, Set<String> moduleDependencies) {
    this.name = Objects.requireNonNull(name);
    this.dependencies = dependencies;
    this.moduleDependencies = moduleDependencies;
  }

  public String getName() {
    return name;
  }

  public Set<DependencyDto> getDependencies() {
    return dependencies;
  }

  public Set<String> getModuleDependencies() {
    return moduleDependencies;
  }
}
