package dev.nk7.demon.server.domain

import org.neo4j.ogm.annotation.*
import java.time.Instant

@NodeEntity("Project")
data class Project(
  @Id
  /**
   * Должен формироваться по правилу name:branch
   */
  val id: String,
  @Property("name")
  val name: String,
  @Property("updated")
  val updated: Instant,
  @Property("branch")
  val branch: String,
  @Relationship(type = "DEPENDS_ON", direction = Relationship.Direction.UNDIRECTED)
  val dependencies: Set<Dependency>,
  @Relationship(type = "HAS_MODULE", direction = Relationship.Direction.UNDIRECTED)
  val modules: Set<Module>
)
