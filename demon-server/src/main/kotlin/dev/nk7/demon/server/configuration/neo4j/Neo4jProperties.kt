package dev.nk7.demon.server.configuration.neo4j

import io.micronaut.context.annotation.ConfigurationProperties


@ConfigurationProperties(value = "application.neo4j")
data class Neo4jProperties(
  val uri: String,
  val username: String,
  val password: String
)
