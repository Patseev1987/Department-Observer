

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

    //Dagger2
//    api(libs.dagger)
//    ksp(libs.daggerCompiler)

    //Room
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
    implementation(libs.room.runtime)
    testImplementation(libs.room.test)

    //tests
    testImplementation(libs.kotlinx.coroutines.test)
    implementation(libs.mockito.kotlin)
}