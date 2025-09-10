plugins {
    alias(libs.plugins.patseev.plugin.library)
}


android {
    namespace = "ru.bogdan.core_ui"
    compileSdk = 36

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        defaultConfig{
            missingDimensionStrategy("environment","prod")
            missingDimensionStrategy("environment","dev")
        }
    }
}

dependencies {
    implementation(project(path = ":modules:core:core_api"))
    implementation(libs.accompanist.systemuicontroller)
}

