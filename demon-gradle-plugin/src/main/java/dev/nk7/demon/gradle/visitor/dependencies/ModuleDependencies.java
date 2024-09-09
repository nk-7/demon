package dev.nk7.demon.gradle.visitor.dependencies;

import dev.nk7.demon.api.v1.dto.DependencyDto;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ModuleDependencies {

  private final Map<String, Set<DependencyDto>> externalDependencies;
  private final Map<String, Set<String>> interModulesDependencies;


  public ModuleDependencies() {
    this.externalDependencies = new HashMap<>();
    this.interModulesDependencies = new HashMap<>();
  }

  public Map<String, Set<DependencyDto>> getExternalDependencies() {
    return externalDependencies;
  }

  public Map<String, Set<String>> getInterModulesDependencies() {
    return interModulesDependencies;
  }
}
