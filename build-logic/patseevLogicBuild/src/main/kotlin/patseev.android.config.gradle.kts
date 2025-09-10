androidConfig {
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
        aidl = false
        viewBinding = false
    }

}

kotlin {
    jvmToolchain(17)
}

dependencies {
    add("implementation", libs.androidx.core.ktx)
    add("implementation", libs.androidx.lifecycle.runtime.ktx)
    add("implementation", libs.androidx.activity.compose)
    add("implementation", libs.androidx.lifecycle.viewmodel.compose)
    add("implementation", platform(libs.androidx.compose.bom))
    add("implementation", libs.androidx.ui)
    add("implementation", libs.androidx.ui.graphics)
    add("implementation", libs.androidx.ui.tooling.preview)
    add("implementation", libs.androidx.material3)
    add("implementation", libs.dagger)
    add("ksp", libs.daggerCompiler)
    add("testImplementation", libs.junit)
    add("androidTestImplementation", libs.androidx.junit)
    add("androidTestImplementation", libs.androidx.espresso.core)
    add("androidTestImplementation", platform(libs.androidx.compose.bom))
    add("androidTestImplementation", libs.androidx.ui.test.junit4)
    add("debugImplementation", libs.androidx.ui.tooling)
    add("debugImplementation", libs.androidx.ui.test.manifest)
}