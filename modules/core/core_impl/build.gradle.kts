plugins {
    alias(libs.plugins.patseev.plugin.library)
    alias(libs.plugins.detect)
}

detekt {
    toolVersion = libs.versions.detekt.get()
    config.setFrom(files("$rootDir/config/detekt/detekt.yml", "$rootDir/detekt-rules/src/main/resources/config.yml"))
    buildUponDefaultConfig = true
}

android {
    namespace = "ru.bogdan.core_impl"

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        buildConfig = true
    }

    flavorDimensions += "environment"
    productFlavors {
        create("dev") {
            dimension = "environment"
            buildConfigField(
                "String",
                "BASE_URL",
                project.properties["BASE_URL_TEST"].toString()
            )
        }
        create("prod") {
            dimension = "environment"
            buildConfigField(
                "String",
                "BASE_URL",
                project.properties["BASE_URL_PROD"].toString()
            )
        }
    }
}

dependencies {
    api(project(path = ":modules:core:core_api"))

    //DataStore
    implementation(libs.androidx.datastore)
    api(libs.androidx.navigation.compose)

    //Ktor
    implementation(libs.ktor.core)
    implementation(libs.ktor.cio)
    implementation(libs.ktor.negotiation)
    implementation(libs.ktor.json)
    implementation(libs.ktor.client.auth)
    implementation(libs.ktor.logging)
    testImplementation(libs.ktor.client.test)

    detektPlugins(project(":detekt-rules"))
}