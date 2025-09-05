plugins {
    alias(libs.plugins.ktor)
    alias(libs.plugins.patseev.plugin.library)
}

android {
    namespace = "ru.bogdan.core_api"

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
    //DataStore
    implementation(libs.androidx.datastore)

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