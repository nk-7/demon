# Gradle DEpendency MONitor plugin (demon).

## About

The Demon-gradle-plugin allows you to build a project dependency tree and send it to the demon-server.

Once the demon-server receives the dependency tree, it saves it to a Neo4j database. By querying this database, you can gain insights into:

Which dependencies are used in the projects
How the modules are connected to each other
How to more effectively address the jar-hell issue
Using the demon-gradle-plugin can be especially useful for products built with a microservice architecture that lack a proper solution for managing shared dependencies, such as a version-catalog or BOM.


## Project Status

Currently, the project is under development and only supports dependency collection with the compileClasspath configuration.



## How to Use the Plugin

In the `build.gradle` file, you need to add the following lines:

```kotlin
plugins {
  id("dev.nk7.demon-gradle-plugin") version "1.0.3"
}
```
For a multi-module project, this should be added to the build.gradle of the parent project.

The plugin adds an extension `demon` that allows you to specify the path to the demon-server REST API.

To configure the correct path to the demon-backend, add the following lines to `build.gradle`:

```kotlin
demon {
  backendBaseUrl.set(uri("http://localhost:8080").toUrl())
}
```
Make sure to specify the correct path.

To send dependency information, you need to run the Gradle task `demon-report`:

```shell
gradle demon-report
```
