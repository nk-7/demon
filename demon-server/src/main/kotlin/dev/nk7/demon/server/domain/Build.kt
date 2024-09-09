package dev.nk7.demon.server.domain

import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship
import java.time.Instant

@NodeEntity(value = "Build")
@Deprecated("Должен быть удален за ненадобностью")
data class Build(
  @Id
  val id: String,
  val timestamp: Instant,
  val branch: String,
  @Relationship(type = "DEPENDS_ON", direction = Relationship.Direction.OUTGOING)
  val dependencies: Set<Dependency>,
  @Relationship(type = "HAS_MODULE", direction = Relationship.Direction.OUTGOING)
  val modules: Set<Module>
)
