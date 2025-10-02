package appPlugin

import androidConfig
import com.android.build.api.dsl.ApplicationDefaultConfig
import libs
import org.gradle.api.Plugin
import org.gradle.api.Project

class AppPlugin : Plugin<Project> {
    override fun apply(target: Project) {

        with(target) {
            with(pluginManager) {
                apply(libs.plugins.android.application.get().pluginId)
                apply(libs.plugins.kotlin.android.get().pluginId)
                apply(libs.plugins.kotlin.compose.get().pluginId)
                apply(libs.plugins.serialization.get().pluginId)
                apply(libs.plugins.ksp.get().pluginId)
                apply(libs.plugins.detect.get().pluginId)
                apply("patseev.android.config")
            }

            androidConfig {
                defaultConfig {
                    this as ApplicationDefaultConfig
                    targetSdk = libs.versions.targetSdk.get().toInt()
                    versionCode = libs.versions.versionCode.get().toInt()
                    versionName = libs.versions.versionName.get()

                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    vectorDrawables {
                        useSupportLibrary = true
                    }
                }
            }
        }
    }
}