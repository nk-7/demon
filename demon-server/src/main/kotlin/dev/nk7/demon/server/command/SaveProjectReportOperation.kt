package dev.nk7.demon.server.command

import dev.nk7.demon.api.v1.dto.DependencyDto
import dev.nk7.demon.api.v1.dto.ProjectReportDto
import dev.nk7.demon.server.domain.Dependency
import dev.nk7.demon.server.domain.Project
import dev.nk7.demon.server.service.ProjectService
import jakarta.inject.Singleton

@Singleton
class SaveProjectReportOperation(private val projectService: ProjectService) : UnitOperation<ProjectReportDto> {
  override suspend fun execute(params: ProjectReportDto) {
    val dependencies = params.dependencies?.map { toDependencyEntity(it) }?.toSet() ?: emptySet()

    val modules = params.modules.map {
      dev.nk7.demon.server.domain.Module(
        "${params.name}:${it.name}:${params.branch}",
        it.name,
        it.dependencies?.map { d -> toDependencyEntity(d) }?.toSet() ?: emptySet(),
        emptySet()
      )
    }.toSet()
    val project =
      Project("${params.name}:${params.branch}", params.name, params.timestamp, params.branch, dependencies, modules)
    projectService.save(project)

  }

  private fun toDependencyEntity(dto: DependencyDto): Dependency {
    if (dto.dependsOn.isEmpty()) {
      return Dependency(dto.id, dto.groupId, dto.artifactId, dto.version, emptySet())
    }
    val dependsOn = dto.dependsOn.map { toDependencyEntity(it) }.toSet()
    return Dependency(dto.id, dto.groupId, dto.artifactId, dto.version, dependsOn)
  }

  private val DependencyDto.id
    get() = "${groupId}_${artifactId}_${version}"

}
