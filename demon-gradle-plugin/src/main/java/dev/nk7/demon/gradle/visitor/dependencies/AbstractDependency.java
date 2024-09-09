package dev.nk7.demon.gradle.visitor.dependencies;

import org.gradle.api.artifacts.component.ComponentIdentifier;
import org.gradle.api.artifacts.component.ModuleComponentIdentifier;
import org.gradle.api.internal.artifacts.DefaultProjectComponentIdentifier;

import java.util.Set;

interface AbstractDependency {

  void flush(ModuleDependencies moduleDependencies, String configurationName);

  static AbstractDependency ofId(ComponentIdentifier id, Set<AbstractDependency> dependsOn) {
    if (id instanceof ModuleComponentIdentifier) {
      final ModuleComponentIdentifier moduleComponentIdentifier = (ModuleComponentIdentifier) id;
      return new ExternalDependency(
        moduleComponentIdentifier.getGroup(),
        moduleComponentIdentifier.getModule(),
        moduleComponentIdentifier.getVersion(),
        dependsOn
      );
    } else if (id instanceof DefaultProjectComponentIdentifier) {
      final DefaultProjectComponentIdentifier projectComponentIdentifier = (DefaultProjectComponentIdentifier) id;
      return new InterModuleDependency(projectComponentIdentifier.getProjectName());
    } else {
      throw new IllegalArgumentException(String.format("Анализа зависимостей типа '%s' пока не поддерживается.", id.getClass().getName()));
    }
  }
}
