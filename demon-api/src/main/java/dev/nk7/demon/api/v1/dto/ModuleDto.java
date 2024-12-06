package dev.nk7.demon.api.v1.dto;

import java.util.Objects;
import java.util.Set;

/**
 * Information about module in multi-modules project.
 */
public class ModuleDto {
  /**
   * Module name.
   */
  private final String name;
  /**
   * All external dependencies.
   */
  private final Set<DependencyDto> dependencies;

  /**
   * Names of modules which depends this module.
   */
  private final Set<String> moduleDependencies;

  /**
   * Constructor for ModuleDto.
   *
   * @param name               Name of module.
   * @param dependencies       Set of external dependencies.
   * @param moduleDependencies Set of modules which depends this module.
   */
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
