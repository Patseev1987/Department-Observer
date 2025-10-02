plugins {
    alias(libs.plugins.kotlin.jvm)
}

group = "ru.patseev"
version = "1"
private val projectJavaVersion: JavaVersion = JavaVersion.toVersion(libs.versions.java.get())
java {
    sourceCompatibility = projectJavaVersion
    targetCompatibility = projectJavaVersion
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

dependencies {
    implementation(libs.kotlinx.coroutines.test)
    implementation(libs.junit)
}
