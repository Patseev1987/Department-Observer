package libraryPlugin

import androidConfig
import com.android.build.api.dsl.ApplicationDefaultConfig
import com.android.build.api.dsl.LibraryDefaultConfig
import libs
import org.gradle.api.Plugin
import org.gradle.api.Project

class LibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.plugins.android.library.get().pluginId)
                apply(libs.plugins.kotlin.android.get().pluginId)
                apply(libs.plugins.kotlin.compose.get().pluginId)
                apply(libs.plugins.serialization.get().pluginId)
                apply(libs.plugins.ksp.get().pluginId)
                apply("patseev.android.config")
            }
            androidConfig {
                defaultConfig {
                    this as LibraryDefaultConfig
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    consumerProguardFiles("consumer-rules.pro")
                }
                buildFeatures {
                    buildConfig = false
                }
            }
        }
    }
}