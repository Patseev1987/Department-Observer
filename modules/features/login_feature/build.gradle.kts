plugins {
    alias(libs.plugins.patseev.plugin.library)
}


android {
    namespace = "ru.bogdan.login_feature"

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
    implementation(project(path = ":modules:core:core_api"))
    implementation(project(path = ":modules:core:core_ui"))
    testImplementation(project(path = ":common-tests"))
}