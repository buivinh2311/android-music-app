pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MusicApplication"
include(":app")
include(":feature-player")
include(":core-ui")
include(":core-resources")
include(":feature-library")
include(":feature-home")
include(":feature-discovery")
include(":feature-album")
include(":feature-recommended")
include(":feature-artist")
include(":feature-recent")
include(":feature-favorite")
include(":feature-playlist")
include(":feature-foryou")
include(":feature-mostheard")
include(":core-model")
include(":core-domain")
include(":core-network")
include(":core-utils")
include(":core-playback")
include(":core-database")
include(":infrastructure")
include(":shared-presentation")
