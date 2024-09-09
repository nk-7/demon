package dev.nk7.demon.gradle;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.assertj.core.api.Assertions;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;

@WireMockTest(httpPort = 8080)
public class DemonPluginTest {
  @Test
  void test(WireMockRuntimeInfo wmi) throws URISyntaxException {
    wmi.getWireMock().register(WireMock.post("/build").willReturn(WireMock.ok()));


    final Project rootProject = ProjectBuilder.builder()
      .withName("root-project")
      .build();
    final Project subprojectA = ProjectBuilder.builder()
      .withName("subprojects-a")
      .withParent(rootProject)
      .build();
    subprojectA.getPluginManager().apply("java");
    subprojectA.getRepositories().mavenCentral();
    subprojectA.getDependencies().add("implementation", "org.apache.commons:commons-lang3:3.17.0");


    final Project subprojectB = ProjectBuilder.builder()
      .withName("subprojects-b")
      .withParent(rootProject)
      .build();
    subprojectB.getPluginManager().apply("java");
    subprojectB.getRepositories().mavenCentral();
    subprojectB.getDependencies().add("implementation", "project(:subproject-a)");


    rootProject.getSubprojects().add(subprojectA);
    rootProject.getSubprojects().add(subprojectB);
    rootProject.getRepositories().mavenCentral();
    rootProject.getPluginManager().apply("java");
    rootProject.getPluginManager().apply("dev.nk7.demon-gradle-plugin");
    rootProject.getExtensions().getByType(DemonPluginConfiguration.class).getBackendBaseUrl().set(new URI("http://localhost:8080"));
    rootProject.getDependencies().add("implementation", "org.springframework.boot:spring-boot-starter-webflux:3.3.3");


    final Task task = rootProject.getTasks().findByName("demon-report");
    Assertions.assertThat(task).isNotNull();
    task.getActions().forEach(t -> t.execute(task));
  }
}
