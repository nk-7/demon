package dev.nk7.demon.server

import dev.nk7.demon.api.v1.DemonApi
import dev.nk7.demon.client.rest.HttpDemonClient
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Property
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.micronaut.test.support.TestPropertyProvider
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.junit.jupiter.api.TestInstance
import org.testcontainers.containers.Neo4jContainer
import org.testcontainers.utility.DockerImageName

@MicronautTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class ComponentTest : TestPropertyProvider {
  @Inject
  protected lateinit var demonClient: DemonApi

  companion object {
    val neo4j = Neo4jContainer(DockerImageName.parse("neo4j:community-ubi9"))
      .apply { start() }
  }


  @Factory
  class TestContext {
    @Singleton
    fun demonClient(@Property(name = "micronaut.server.port") serverPort: Int): DemonApi {
      return HttpDemonClient.fromApiUrl("http://localhost:$serverPort")
    }
  }

  override fun getProperties(): MutableMap<String, String> = mutableMapOf(
    "application.neo4j.uri" to neo4j.getBoltUrl()
  )
}
