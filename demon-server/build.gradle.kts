import io.micronaut.gradle.docker.MicronautDockerfile

plugins {
  alias(mn.plugins.kotlin.jvm)
  alias(mn.plugins.kotlin.noarg)
  alias(mn.plugins.ksp)
  alias(mn.plugins.kotlin.allopen)
  `java-test-fixtures`
  id("io.micronaut.application") version "4.4.3"
  id("io.micronaut.aot") version "4.4.3"
}

group = "dev.nk7"
version = "1.0.0-SNAPSHOT"


dependencies {
  annotationProcessor(mn.micronaut.http.validation)

  implementation(project(":demon-api"))
  implementation(mn.micronaut.runtime)
  implementation(mn.micronaut.kotlin.extension.functions)
  implementation(mn.kotlinx.coroutines.core)
  implementation(mn.micronaut.jackson.databind)
  implementation(mn.jackson.module.kotlin)
  implementation(mn.jackson.module.parameterNames)
  implementation(mn.slf4j.api)
  implementation("org.neo4j:neo4j-ogm-core:4.0.12")
  implementation("org.neo4j:neo4j-ogm-bolt-driver:4.0.12")

  runtimeOnly(mn.micronaut.http.server.netty)
  runtimeOnly(mn.logback.classic)
  runtimeOnly(mn.snakeyaml)


  testImplementation(project(":demon-api-rest"))
  testImplementation(mn.testcontainers.neo4j)
  testImplementation(mn.assertj.core)
  testAnnotationProcessor(mn.micronaut.inject.java)


}
application {
  mainClass = "dev.nk7.demon.server.Application"
}

tasks.named<MicronautDockerfile>("dockerfile") {
  baseImage.set("bellsoft/liberica-openjdk-debian:21.0.5-x86_64")
}

micronaut {
  runtime("netty")
  testRuntime("junit5")
}

val javaVersion = 21

tasks.compileJava {
  options.compilerArgs + "-parameters"
}

kotlin {
  jvmToolchain(javaVersion)
}
noArg {
  annotations(listOf("org.neo4j.ogm.annotation.NodeEntity", "org.neo4j.ogm.annotation.RelationshipEntity"))
}

java {
  toolchain {
    languageVersion.set(JavaLanguageVersion.of(javaVersion))
  }
}
