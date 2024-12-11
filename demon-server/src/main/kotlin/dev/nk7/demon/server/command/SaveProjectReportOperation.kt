package dev.nk7.demon.server.command

import dev.nk7.demon.api.v1.dto.DependenciesReportDto
import dev.nk7.demon.api.v1.dto.DependencyDto
import dev.nk7.demon.server.domain.Dependency
import dev.nk7.demon.server.domain.Project
import dev.nk7.demon.server.service.ProjectService
import jakarta.inject.Singleton

@Singleton
class SaveProjectReportOperation(private val projectService: ProjectService) : UnitOperation<DependenciesReportDto> {
  override suspend fun execute(params: DependenciesReportDto) {
    val dependencies = params.dependencies?.map { toDependencyEntity(it) }?.toSet() ?: emptySet()

    val modules = params.modules.map {
      dev.nk7.demon.server.domain.Module(
        "${params.name}:${it.name}:${params.branch}",
        it.name,
        it.dependencies?.map { d -> toDependencyEntity(d) }?.toSet() ?: emptySet(),
        emptySet()
      )
    }.associateBy { it.name }.toMutableMap()

    // TODO: Make it more readable.
    params.modules.stream()
      .filter { dto -> dto.moduleDependencies.isNotEmpty() }
      .forEach { dto ->
        modules[dto.name] =
          modules[dto.name]!!.copy(moduleDependencies = dto.moduleDependencies.map { md -> modules[md]!! }.toSet())
      }

    val project = Project(
      "${params.name}:${params.branch}",
      params.name,
      params.timestamp,
      params.branch,
      dependencies,
      modules.values.toSet()
    )

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
