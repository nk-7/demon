package dev.nk7.demon.gradle.visitor.dependencies;

import dev.nk7.demon.gradle.visitor.Visitor;
import org.gradle.api.Project;
import org.gradle.api.artifacts.component.ComponentIdentifier;
import org.gradle.api.artifacts.result.DependencyResult;
import org.gradle.api.artifacts.result.ResolvedComponentResult;
import org.gradle.api.artifacts.result.ResolvedDependencyResult;
import org.gradle.api.internal.artifacts.result.DefaultResolvedDependencyResult;
import org.gradle.api.provider.Provider;
import org.gradle.api.tasks.diagnostics.internal.ConfigurationDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class ProjectDependenciesVisitor implements Visitor<Project, ModuleDependencies> {
  private static final Logger log = LoggerFactory.getLogger(ProjectDependenciesVisitor.class);
  private final ConfigurationFilter configurationFilter = new ConfigurationFilter();

  @Override
  public ModuleDependencies visit(Project target) {
    log.warn("Построение графа зависимостей для проекта '{}'.", target.getName());
    final List<ConfigurationDetails> configurations = configurationFilter.filter(target.getConfigurations()).stream()
      .map(ConfigurationDetails::of)
      .collect(Collectors.toList());
    return projectDependencies(configurations);
  }

  private ModuleDependencies projectDependencies(List<ConfigurationDetails> configurationDetails) {
    final ModuleDependencies moduleDependencies = new ModuleDependencies();


    for (final ConfigurationDetails cd : configurationDetails) {
      configurationDependencies(cd).forEach(ad -> ad.flush(moduleDependencies, cd.getName()));
    }
    return moduleDependencies;
  }

  private Set<AbstractDependency> configurationDependencies(ConfigurationDetails configuration) {
    log.debug("Построение графа зависимостей для конфигурации '{}'.", configuration.getName());
    final Provider<ResolvedComponentResult> rootProvider = configuration.getResolutionResultRoot();
    if (rootProvider == null) {
      return Collections.emptySet();
    }
    final ResolvedComponentResult root = rootProvider.get();

    final Set<AbstractDependency> dependencies = new HashSet<>();
    for (final DependencyResult dependency : root.getDependencies()) {
      final Set<ComponentIdentifier> visited = new HashSet<>();
      if (dependency instanceof DefaultResolvedDependencyResult) {
        final DefaultResolvedDependencyResult resolvedDependency = (DefaultResolvedDependencyResult) dependency;
        walkDependenciesTree(resolvedDependency.getSelected(), visited)
          .ifPresent(dependencies::add);
      }
    }
    return dependencies;
  }

  private Optional<AbstractDependency> walkDependenciesTree(ResolvedComponentResult root, Set<ComponentIdentifier> visited) {
    if (!visited.add(root.getId())) {
      return Optional.empty();
    }
    // Цикл обхода дерева зависимостей.
    final Set<AbstractDependency> dependencies = new HashSet<>();

    for (final DependencyResult dependency : root.getDependencies()) {
      if (dependency instanceof ResolvedDependencyResult) {
        final ResolvedDependencyResult resolvedDependency = (ResolvedDependencyResult) dependency;
        if (!resolvedDependency.isConstraint()) {
          walkDependenciesTree(resolvedDependency.getSelected(), visited)
            .ifPresent(dependencies::add);
        }
      }
    }
    return Optional.of(AbstractDependency.ofId(root.getId(), dependencies));
  }
}
