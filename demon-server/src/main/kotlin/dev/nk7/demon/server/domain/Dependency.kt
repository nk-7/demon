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
) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Dependency

    if (id != other.id) return false
    if (groupId != other.groupId) return false
    if (artifactId != other.artifactId) return false
    return version == other.version
  }

  override fun hashCode(): Int {
    var result = id.hashCode()
    result = 31 * result + groupId.hashCode()
    result = 31 * result + artifactId.hashCode()
    result = 31 * result + version.hashCode()
    return result
  }
}
