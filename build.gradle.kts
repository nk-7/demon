import org.jreleaser.model.Active
import org.jreleaser.model.Distribution
import org.jreleaser.model.Stereotype
import kotlin.io.path.Path

plugins {
  java
  `maven-publish`
  `java-library`
  id("org.jreleaser") version "1.15.0"
}

allprojects {
  group = "dev.nk7.demon"
  version = "1.0.0-SNAPSHOT"
}
subprojects {
  group = project.group
  apply {
    plugin("java-library")
    plugin("java")
    plugin(MavenPublishPlugin::class.java)
  }

  tasks {
    val javaVersion = 11
    compileJava {
      options.compilerArgs.add("-parameters")
    }
    java {
      toolchain {
        languageVersion.set(JavaLanguageVersion.of(javaVersion))
      }
    }
    test {
      useJUnitPlatform()
    }
  }
  publishing {
    repositories {
      maven {
        url = uri(
          if (version.toString().endsWith("SNAPSHOT")) {
            project.properties["repo.snapshots.url"].toString()
          } else {
            project.properties["repo.releases.url"].toString()
          }
        )
        credentials {
          username = project.properties["repo.username"].toString()
          password = project.properties["repo.password"].toString()
        }
      }
    }
    publications {
      create<MavenPublication>("artifact") {
        groupId = project.group.toString()
        artifactId = project.name
        version = project.version.toString()
        from(components["java"])
      }
    }
  }

}

