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
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Department observer"
include(":app")
include(":modules")
include(":modules:features")
include(":modules:core")
include(":modules:base")
include(":modules:ui")
include(":base_network")
include(":modules:core:core_api")
include(":modules:core:core_ui")
include(":modules:features:login_feature")
include(":modules:features:mechanic-feature")

