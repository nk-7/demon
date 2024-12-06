package dev.nk7.demon.server.controller

import dev.nk7.demon.api.v1.dto.DependenciesReportDto
import dev.nk7.demon.server.command.SaveProjectReportOperation
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import org.slf4j.LoggerFactory


@Controller(value = "/build", consumes = [MediaType.APPLICATION_JSON], produces = [MediaType.APPLICATION_JSON])
open class BuildController(private val saveProjectReportOperation: SaveProjectReportOperation) {
  private val log = LoggerFactory.getLogger(BuildController::class.java)

  @Post
  suspend fun post(@Body projectDto: DependenciesReportDto): HttpResponse<Unit> {
    log.info("Получен отчет о сборке: '{}.'", projectDto)
    saveProjectReportOperation.execute(projectDto)
    return HttpResponse.ok()
  }
}
