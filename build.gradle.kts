plugins {
  java
  `maven-publish`
  `java-library`
}

allprojects {
  group = "dev.nk7"
  version = "1.0.1"
}
subprojects {
  group = project.group

  apply {
    plugin("java-library")
    plugin("java")
    plugin(MavenPublishPlugin::class.java)
  }

  tasks {
    compileJava {
      options.compilerArgs.add("-parameters")
//      sourceCompatibility = "1.8"
//      targetCompatibility = "1.8"
    }
    java {
      toolchain {
        languageVersion.set(JavaLanguageVersion.of("8"))
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

