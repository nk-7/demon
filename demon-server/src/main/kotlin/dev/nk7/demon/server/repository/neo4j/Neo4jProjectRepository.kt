package dev.nk7.demon.server.repository.neo4j

import dev.nk7.demon.server.domain.Project
import dev.nk7.demon.server.repository.ProjectRepository
import jakarta.inject.Singleton
import org.neo4j.ogm.session.SessionFactory

@Singleton
class Neo4jProjectRepository(private val sessionFactory: SessionFactory) : ProjectRepository {
  override suspend fun save(project: Project) {
    val session = sessionFactory.openSession()
    session.save(project)
  }
}
