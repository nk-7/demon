package dev.nk7.demon.server.controller

import dev.nk7.demon.server.ComponentTest
import dev.nk7.demon.server.TestReport
import dev.nk7.demon.server.domain.Module
import dev.nk7.demon.server.domain.Project
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class BuildControllerTest : ComponentTest() {
  private companion object {
    const val API_MODULE_NAME = "test-project-api"
    const val CLIENT_MODULE_NAME = "test-project-client"
  }

  @Test
  fun postBuildReport() {
    val report = TestReport.report()
    demonClient.sendDependenciesReport(report)

    val session = sessionFactory.openSession()

    val project = session.load(Project::class.java, report.name + ":" + report.branch)
    Assertions.assertThat(project).isNotNull
    Assertions.assertThat(project.modules).hasSize(2)
    Assertions.assertThat(project.modules).extracting("name").contains(API_MODULE_NAME, CLIENT_MODULE_NAME)

    val modules = project.modules.map { session.load(Module::class.java, it.id) }.associateBy { it.name }
    Assertions.assertThat(modules).containsKeys(API_MODULE_NAME, CLIENT_MODULE_NAME)

    val clientModule = modules[CLIENT_MODULE_NAME] ?: throw IllegalStateException()
    val apiModule = modules[API_MODULE_NAME] ?: throw IllegalStateException()
    Assertions.assertThat(clientModule.moduleDependencies).contains(apiModule)
  }
}
