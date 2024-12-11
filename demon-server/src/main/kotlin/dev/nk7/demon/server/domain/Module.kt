package dev.nk7.demon.server.domain

import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship

@NodeEntity(value = "Module")
data class Module(
  @Id
  var id: String?,
  val name: String,
  @Relationship(type = "DEPENDS_ON", direction = Relationship.Direction.UNDIRECTED)
  val dependencies: Set<Dependency>,
  @Relationship(type = "DEPENDS_ON", direction = Relationship.Direction.OUTGOING)
  val moduleDependencies: Set<Module>
) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Module

    if (id != other.id) return false
    return name == other.name
  }

  override fun hashCode(): Int {
    var result = id?.hashCode() ?: 0
    result = 31 * result + name.hashCode()
    return result
  }
}
