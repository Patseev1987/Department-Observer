pluginManagement {
    includeBuild("build-logic")
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
include(":base_network")
include(":modules:core:core_api")
include(":modules:core:core_ui")
include(":modules:features:login_feature")
include(":modules:features:home_screen_feature")
include(":modules:features:machine_list")
include(":modules:core:core_factory")
include(":modules:core:core_impl")

include("common-tests")
include("detekt-rules")