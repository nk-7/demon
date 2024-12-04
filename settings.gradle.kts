rootProject.name = "demon"
plugins {
  id("io.micronaut.platform.catalog") version "4.4.3"
}

include("demon-api")
include("demon-api-rest")
include("demon-server")
include("demon-gradle-plugin")
