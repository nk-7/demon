package dev.nk7.demon.gradle.visitor.dependencies;

import java.util.HashSet;
import java.util.Objects;

public class InterModuleDependency implements AbstractDependency {

  private final String name;

  public InterModuleDependency(String name) {
    this.name = Objects.requireNonNull(name);
  }

  @Override
  public void flush(ModuleDependencies moduleDependencies, String configurationName) {
    moduleDependencies.getInterModulesDependencies().computeIfAbsent(configurationName, key -> new HashSet<>()).add(name);

  }
}
