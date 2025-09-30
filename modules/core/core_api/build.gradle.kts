plugins {
    alias(libs.plugins.ktor)
    alias(libs.plugins.patseev.plugin.library)
}

android {
    namespace = "ru.bogdan.core_api"

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    defaultConfig {
        missingDimensionStrategy("environment", "prod")
        missingDimensionStrategy("environment", "dev")
    }
}