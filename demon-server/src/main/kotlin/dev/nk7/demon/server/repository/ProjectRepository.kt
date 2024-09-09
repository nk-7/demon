package dev.nk7.demon.server.repository

import dev.nk7.demon.server.domain.Project


interface ProjectRepository {
  suspend fun save(project: Project)
}

