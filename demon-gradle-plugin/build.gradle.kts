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
  api(platform(libs.bom.junit))
  shadow(gradleApi())
  implementation(project(":demon-api"))
  implementation(project(":demon-api-rest"))
  compileOnly(libs.slf4j)
  implementation("org.eclipse.jgit:org.eclipse.jgit:6.10.0.202406032230-r")
  testImplementation(libs.bundles.test)
  testImplementation(libs.wiremock)
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



