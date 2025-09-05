plugins {
    alias(libs.plugins.patseev.plugin.app)
}


android {
    namespace = "ru.bogdan.departmentobserver"

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

android {
    namespace = "ru.bogdan.departmentobserver"
}


dependencies {
    //modules
    implementation(project(":modules:core:core_ui"))
    implementation(project(":modules:core:core_api"))
    implementation(project(":modules:features:login_feature"))
    implementation(project(":modules:features:home_screen_feature"))

    implementation(libs.androidx.navigation.compose)
}