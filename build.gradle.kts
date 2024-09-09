plugins {
    kotlin("jvm") version "2.0.20"
}

group = "dev.nk7"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

val javaVersion = 21

kotlin {
    jvmToolchain(javaVersion)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(javaVersion))
    }
}
tasks.test {
    useJUnitPlatform()
}

