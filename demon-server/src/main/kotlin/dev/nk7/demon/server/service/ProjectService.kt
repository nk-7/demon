package dev.nk7.demon.server.service

import dev.nk7.demon.server.domain.Project
import dev.nk7.demon.server.repository.ProjectRepository
import jakarta.inject.Singleton


@Singleton
open class ProjectService(private val projectRepository: ProjectRepository) {
  suspend fun save(project: Project) {
    projectRepository.save(project)
  }
}
