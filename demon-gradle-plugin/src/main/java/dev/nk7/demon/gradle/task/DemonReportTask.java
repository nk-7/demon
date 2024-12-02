package dev.nk7.demon.gradle.task;

import dev.nk7.demon.api.v1.DemonApi;
import dev.nk7.demon.api.v1.dto.ModuleDto;
import dev.nk7.demon.api.v1.dto.ProjectReportDto;
import dev.nk7.demon.client.rest.HttpDemonClient;
import dev.nk7.demon.gradle.DemonPluginConfiguration;
import dev.nk7.demon.gradle.visitor.dependencies.ModuleDependencies;
import dev.nk7.demon.gradle.visitor.dependencies.ProjectDependenciesVisitor;
import dev.nk7.demon.gradle.visitor.git.GitInfo;
import dev.nk7.demon.gradle.visitor.git.GitInfoVisitor;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class DemonReportTask extends DefaultTask {
  private static final Logger log = LoggerFactory.getLogger(DemonReportTask.class);
  private final ProjectDependenciesVisitor projectDependenciesVisitor = new ProjectDependenciesVisitor();
  private final GitInfoVisitor gitInfoVisitor = new GitInfoVisitor();

//  private final DemonPluginConfiguration configuration;

//  @Inject
//  public DemonReportTask(DemonPluginConfiguration configuration) {
//    this.configuration = Objects.requireNonNull(configuration);
//  }

  @TaskAction
  void sendReport() {
    final String backendBaseUrl = getProject().getExtensions().getByType(DemonPluginConfiguration.class).getBackendBaseUrl().get().toString();
    final ModuleDependencies moduleDependencies = projectDependenciesVisitor.visit(getProject());
    final GitInfo gitInfo = gitInfoVisitor.visit(getProject());
    final Set<ModuleDto> modules = modules();

    final ProjectReportDto reportDto = new ProjectReportDto(
      Instant.now(),
      getProject().getName(),
      gitInfo.getBranch() == null ? "NO_VCS" : gitInfo.getBranch(),
      moduleDependencies.getExternalDependencies().get("compileClasspath"),
      modules
    );
    final DemonApi api = HttpDemonClient.fromApiUri(backendBaseUrl);
    try {
      api.sendBuildReport(reportDto);
    } catch (Exception e) {
      getProject().getLogger().warn("Cannot send demon dependencies report.", e);
    }

  }

  private Set<ModuleDto> modules() {
    if (getProject().getSubprojects().isEmpty()) {
      return Collections.emptySet();
    }
    return getProject().getSubprojects().stream()
      .map(this::moduleDto)
      .collect(Collectors.toSet());
  }

  private ModuleDto moduleDto(Project module) {
    log.warn("Получение зависимостей модуля '{}'.", module.getName());
    final ModuleDependencies moduleDependencies = projectDependenciesVisitor.visit(module);
    return new ModuleDto(
      module.getName(),
      moduleDependencies.getExternalDependencies().get("compileClasspath"),
      moduleDependencies.getInterModulesDependencies().get("compileClasspath"));
  }
}
