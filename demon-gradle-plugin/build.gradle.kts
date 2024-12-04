import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
  id("com.gradleup.shadow") version "9.0.0-beta2"
  `java-gradle-plugin`
  id("com.gradle.plugin-publish") version "1.2.1"
}
apply(plugin = "com.gradle.plugin-publish")


tasks.named("shadowJar", ShadowJar::class) {
  enableRelocation = true
  relocationPrefix = "libs"
  archiveClassifier.set("")
}

dependencies {
  api(platform("org.junit:junit-bom:5.11.0"))
  shadow(gradleApi())
  implementation(project(":demon-api"))
  implementation(project(":demon-api-rest"))
  compileOnly("org.slf4j:slf4j-api:2.0.16")
  implementation("org.eclipse.jgit:org.eclipse.jgit:6.10.0.202406032230-r")
  testImplementation("org.assertj:assertj-core:3.26.3")
  testImplementation("org.junit.jupiter:junit-jupiter")
  testImplementation("org.junit.jupiter:junit-jupiter-engine")
  testImplementation("org.wiremock:wiremock:3.9.2")
}

gradlePlugin {
  plugins {
    create("demon-plugin") {
      id = "dev.nk7.demon-gradle-plugin"
      implementationClass = "dev.nk7.demon.gradle.DemonPlugin"
      displayName = "Demon Gradle plugin."
      description = "Plugin that help to collect and analyze dependencies across multiple projects."
      version = project.version.toString()
    }
  }
}



