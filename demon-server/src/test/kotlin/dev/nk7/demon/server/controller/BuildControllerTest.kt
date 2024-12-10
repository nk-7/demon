package dev.nk7.demon.server.controller

import dev.nk7.demon.server.ComponentTest
import dev.nk7.demon.server.TestReport
import dev.nk7.demon.server.domain.Project
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class BuildControllerTest : ComponentTest() {
  @Test
  fun postBuildReport() {
    val report = TestReport.report()
    demonClient.sendDependenciesReport(report)

    val session = sessionFactory.openSession()

    val project = session.load(Project::class.java, report.name + ":" + report.branch)
    Assertions.assertThat(project).isNotNull
    Assertions.assertThat(project.modules).hasSize(2)

  }
}
