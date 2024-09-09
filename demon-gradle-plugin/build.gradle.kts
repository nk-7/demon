plugins {
  `java-gradle-plugin`
  id("com.gradle.plugin-publish") version "1.2.1"
}
apply(plugin = "com.gradle.plugin-publish")

dependencies {
  api(platform(libs.bom.junit))
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
    }
  }
}



