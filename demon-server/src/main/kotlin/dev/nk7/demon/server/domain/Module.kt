package dev.nk7.demon.server.domain

import org.neo4j.ogm.annotation.GeneratedValue
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship
import org.neo4j.ogm.id.UuidStrategy

@NodeEntity(value = "Module")
data class Module(
  @Id @GeneratedValue(strategy = UuidStrategy::class)
  var id: String?,
  val name: String,
  @Relationship(type = "DEPENDS_ON", direction = Relationship.Direction.UNDIRECTED)
  val dependencies: Set<Dependency>,
  @Relationship(type = "DEPENDS_ON", direction = Relationship.Direction.OUTGOING)
  val moduleDependencies: Set<Module>
)
