plugins {
  `maven-publish`
}
dependencies {
  api(platform(libs.bom.okhttp))
  api(platform(libs.bom.jackson))

  implementation(project(":demon-api"))

  implementation(libs.okhttp)
  implementation(libs.jackson.databind)
  implementation(libs.jackson.module.jsr310)

  implementation(libs.slf4j)
  compileOnly(libs.logback.classic)
}
