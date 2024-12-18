import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
  id("com.gradleup.shadow") version "9.0.0-beta2"
  `java-gradle-plugin`
  id("com.gradle.plugin-publish") version "1.2.1"
  signing
}



dependencies {
  api(platform("org.junit:junit-bom:5.11.0"))
  shadow(gradleApi())
  implementation(project(":demon-api"))
  implementation(project(":demon-api-rest"))
  compileOnly("org.slf4j:slf4j-api:2.0.16")
  implementation("org.eclipse.jgit:org.eclipse.jgit:5.13.3.202401111512-r")
  testImplementation("org.assertj:assertj-core:3.26.3")
  testImplementation("org.junit.jupiter:junit-jupiter")
  testImplementation("org.junit.jupiter:junit-jupiter-engine")
  testImplementation("com.github.tomakehurst:wiremock-jre8-standalone:2.35.1")
}

gradlePlugin {
  website = "https://github.com/nk-7/demon"
  vcsUrl = "https://github.com/nk-7/demon"
  version = project.version
  plugins {
    create("demon-plugin") {
      id = "dev.nk7.demon-gradle-plugin"
      implementationClass = "dev.nk7.demon.gradle.DemonPlugin"
      displayName = "Demon Gradle plugin."
      description = "Plugin that help to collect and analyze dependencies across multiple projects."
      version = project.version.toString()
      tags = listOf("tools", "dependencies", "analyzer", "dependency-graph")
    }
  }
}
tasks.named("shadowJar", ShadowJar::class) {
  enableRelocation = true
  relocationPrefix = "libs"
  archiveClassifier.set("")
}



