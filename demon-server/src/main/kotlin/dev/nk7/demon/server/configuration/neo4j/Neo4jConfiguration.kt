package dev.nk7.demon.server.configuration.neo4j

import io.micronaut.context.annotation.Factory
import jakarta.inject.Singleton
import org.neo4j.ogm.session.SessionFactory


@Factory
open class Neo4jConfiguration(private val neo4jProperties: Neo4jProperties) {

  @Singleton
  open fun sessionFactory(): SessionFactory {
    val configuration = org.neo4j.ogm.config.Configuration.Builder()
      .uri(neo4jProperties.uri)
      .credentials(neo4jProperties.username, neo4jProperties.password)
      .build()
    return SessionFactory(configuration, "dev.nk7.demon.server.domain")

  }
}
