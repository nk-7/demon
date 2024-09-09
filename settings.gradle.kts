rootProject.name = "demon"
dependencyResolutionManagement {
    repositories {
        maven {
            url = uri(extra.properties["repo.snapshots.url"].toString())
            credentials.username = extra.properties["repo.username"].toString()
            credentials.password = extra.properties["repo.password"].toString()
        }
        maven {
            url = uri(extra.properties["repo.releases.url"].toString())
            credentials.username = extra.properties["repo.username"].toString()
            credentials.password = extra.properties["repo.password"].toString()
        }
    }
    versionCatalogs {
        create("libs") {
            from("dev.nk7.core:nk7-versions-catalog:1.0.1-SNAPSHOT")
        }
    }
}


