package dev.nk7.demon.gradle;

import dev.nk7.demon.gradle.task.DemonReportTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.net.URI;
import java.net.URISyntaxException;

public class DemonPlugin implements Plugin<Project> {
  @Override
  public void apply(Project project) {
    try {
      final DemonPluginConfiguration configuration = project.getExtensions().create("demon", DemonPluginConfiguration.class);
      configuration.getBackendBaseUrl().convention(new URI("http://localhost:8080"));
      project.getTasks().register(
        "demon-report",
        DemonReportTask.class,
        t -> {
          t.setGroup("demon");
        }
      );
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }
}
