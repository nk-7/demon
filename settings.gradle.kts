rootProject.name = "demon"
pluginManagement {
  resolutionStrategy {
    eachPlugin {
      if (requested.id.namespace == "dev.nk7") {
        useModule("dev.nk7:demon-gradle-plugin:1.0.0-SNAPSHOT")
      }
    }
  }
  repositories {
    maven {
      url = uri(extra.properties["repo.snapshots.url"].toString())
      credentials.username = extra.properties["repo.username"].toString()
      credentials.password = extra.properties["repo.password"].toString()
    }
    maven {
      url = uri(extra.properties["repo.releases.url"].toString())
      credentials.username = extra.properties["repo.username"].toString()
      credentials.password = extra.properties["repo.password"].toString()
    }
    gradlePluginPortal()
  }
}
plugins {
  id("io.micronaut.platform.catalog") version "4.4.3"
}

include("demon-api")
include("demon-api-rest")
include("demon-server")
include("demon-gradle-plugin")
