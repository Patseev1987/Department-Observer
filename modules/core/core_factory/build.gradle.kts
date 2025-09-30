plugins {
    alias(libs.plugins.patseev.plugin.library)
}


android {
    namespace = "ru.bogdan.core_factory"

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

dependencies {
    implementation(project(path = ":modules:core:core_impl"))
}