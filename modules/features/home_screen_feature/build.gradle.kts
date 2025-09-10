plugins {
    alias(libs.plugins.patseev.plugin.library)
}


android {
    namespace = "ru.bogdan.main_screen_feature"

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    kotlinOptions {
        freeCompilerArgs = listOf("-XXLanguage:+PropertyParamAnnotationDefaultTargetMode")
    }
    defaultConfig{
        missingDimensionStrategy("environment","prod")
        missingDimensionStrategy("environment","dev")
    }
}

dependencies {
    implementation(project(path = ":modules:core:core_api"))
    implementation(project(path = ":modules:core:core_ui"))
    //Coil
    implementation(libs.coil.network)
    implementation(libs.coil)
}