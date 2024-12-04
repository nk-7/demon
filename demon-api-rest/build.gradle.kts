plugins {
  `maven-publish`
}
dependencies {
  api(platform("com.fasterxml.jackson:jackson-bom:2.17.2"))
  implementation(project(":demon-api"))
  implementation("com.fasterxml.jackson.core:jackson-databind")
  implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
  compileOnly("org.slf4j:slf4j-api:2.0.16")
  compileOnly("ch.qos.logback:logback-classic:1.5.8")
}
