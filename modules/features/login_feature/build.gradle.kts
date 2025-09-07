plugins {
    alias(libs.plugins.patseev.plugin.library)
}


android {
    namespace = "ru.bogdan.login_feature"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

}

dependencies {
    implementation(project(path = ":modules:core:core_api"))
    implementation(project(path = ":modules:core:core_ui"))
}