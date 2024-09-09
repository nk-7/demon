package dev.nk7.demon.server.controller

import dev.nk7.demon.api.v1.dto.DependencyDto
import dev.nk7.demon.api.v1.dto.ProjectReportDto
import dev.nk7.demon.server.ComponentTest
import org.junit.jupiter.api.Test
import java.time.Instant

class BuildControllerTest : ComponentTest() {
  @Test
  fun postBuildReport() {
    demonClient.sendBuildReport(
      ProjectReportDto(
        Instant.now(),
        "test",
        "test",
        setOf(
          DependencyDto(
            "g1", "a1", "1.0.0", setOf(DependencyDto("g2", "a2", "3.1.14", emptySet()))
          )
        ),
        setOf()
      )
    )
  }
}


