package dev.nk7.demon.server

import dev.nk7.demon.api.v1.dto.DependenciesReportDto
import dev.nk7.demon.api.v1.dto.DependencyDto
import dev.nk7.demon.api.v1.dto.ModuleDto
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.regex.Pattern

object TestReport {

  private val timestamp: Instant = LocalDateTime.of(2024, 12, 9, 3, 14, 15)
    .atZone(ZoneId.of("Europe/Moscow")).toInstant()

  fun report(): DependenciesReportDto {
    val rootDependencies = listOf(
      "org.junit.jupiter:junit-jupiter-api:5.11.3",
      "org.slf4j:slf4j-api:2.0.16",
      "org.jetbrains.kotlin:kotlin-stdlib:2.1.0"
    ).map { dependency(it) }.toSet()


    val apiModule = module("test-project-api")
    val apiClientModule = module(
      "test-project-client",
      listOf("com.fasterxml.jackson.core:jackson-databind:2.18.2").map { dependency(it) }.toSet(),
      setOf("test-project-api")
    )

    return DependenciesReportDto(
      timestamp,
      "test-project-parent",
      "main",
      rootDependencies,
      setOf(apiModule, apiClientModule)
    )
  }

  private fun module(
    name: String,
    dependencies: Set<DependencyDto> = emptySet(),
    moduleDependencies: Set<String> = emptySet()
  ): ModuleDto {
    return ModuleDto(name, dependencies, moduleDependencies)
  }

  private fun dependency(gav: String, dependsOn: Set<String> = emptySet()): DependencyDto {
    val parts = gav.trim().split(Pattern.compile("\\s*:\\s*"))
    if (parts.size != 3) {
      throw IllegalArgumentException("Cannot parse GAV dependency notation, should be \"groupId:artifactId:version\", but was '$gav'")
    }
    val depends = dependsOn.map { dependency(it) }.toSet()
    return DependencyDto(parts[0], parts[1], parts[2], depends)
  }

}
