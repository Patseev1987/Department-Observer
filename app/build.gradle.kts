plugins {
    alias(libs.plugins.patseev.plugin.app)
}

detekt {
    toolVersion = libs.versions.detekt.get()
    config.setFrom(files("$rootDir/config/detekt/detekt.yml", "$rootDir/detekt-rules/src/main/resources/config.yml"))
    buildUponDefaultConfig = true
}

android {
    namespace = "ru.bogdan.departmentobserver"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

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

    //tests
    testImplementation(project(path = ":common-tests"))
    androidTestImplementation("com.kaspersky.android-components:kaspresso:1.6.0")
    androidTestImplementation("com.kaspersky.android-components:kaspresso-compose-support:1.6.0")
    androidTestImplementation("io.github.kakaocup:compose:1.0.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.9.2")
    androidTestImplementation("androidx.test:rules:1.7.0")
    androidTestImplementation("androidx.test:runner:1.7.0")
    androidTestImplementation("androidx.test.ext:junit-ktx:1.3.0")

    //detekt
    detektPlugins(project(":detekt-rules"))
}