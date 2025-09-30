plugins {
    alias(libs.plugins.patseev.plugin.app)
}


android {
    namespace = "ru.bogdan.departmentobserver"

    buildTypes {
        release {
            isMinifyEnabled = true
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
            applicationIdSuffix = ".dev"
        }
        create("prod") {
            dimension = "environment"
        }
    }
}

dependencies {
    //modules
    implementation(project(":modules:core:core_ui"))
    implementation(project(":modules:core:core_api"))
    implementation(project(":modules:core:core_factory"))
    implementation(project(":modules:features:login_feature"))
    implementation(project(":modules:features:home_screen_feature"))
    implementation(project(":modules:features:machine_list"))

    implementation(libs.androidx.navigation.compose)
}