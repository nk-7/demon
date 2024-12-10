package dev.nk7.demon.server.domain

import org.neo4j.ogm.annotation.*
import org.neo4j.ogm.id.UuidStrategy


@RelationshipEntity
data class BetweenModulesDependency(
  @Id @GeneratedValue(strategy = UuidStrategy::class)
  var id: String,
  @StartNode
  val from: Module,
  @EndNode
  val to: Module
)
