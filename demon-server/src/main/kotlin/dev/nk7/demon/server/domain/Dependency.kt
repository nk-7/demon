package dev.nk7.demon.server.domain

import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship


@NodeEntity("Dependency")
data class Dependency(
  @Id
  val id: String,
  val groupId: String,
  val artifactId: String,
  val version: String,
  @Relationship(type = "DEPENDS_ON", direction = Relationship.Direction.OUTGOING)
  val dependencies: Set<Dependency>
)
