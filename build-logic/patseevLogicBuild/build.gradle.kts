import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "ru.patseev.bogdan"

dependencies {
    implementation(libs.gradleplugin.android)
    implementation(libs.gradleplugin.compose)
    implementation(libs.gradleplugin.composeCompiler)
    implementation(libs.gradleplugin.kotlin)
    implementation(libs.gradleplugin.api)

    compileOnly(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}

private val projectJavaVersion: JavaVersion = JavaVersion.toVersion(libs.versions.java.get())

java {
    sourceCompatibility = projectJavaVersion
    targetCompatibility = projectJavaVersion
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}


tasks.withType<KotlinCompile>().configureEach {
    compilerOptions.jvmTarget.set(JvmTarget.fromTarget(projectJavaVersion.toString()))
}

gradlePlugin {
    plugins {
        register("patseev-appPlugin") {
            id = "patseev-appPlugin"
            group = "patseev"
            implementationClass = "appPlugin.AppPlugin"
        }
    }

    plugins {
        register("patseev-libraryPlugin") {
            id = "patseev-libraryPlugin"
            group = "patseev"
            implementationClass = "libraryPlugin.LibraryPlugin"
        }
    }
}