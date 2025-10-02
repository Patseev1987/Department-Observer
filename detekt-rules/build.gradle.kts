plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.jreleaser)
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
}

dependencies {
    compileOnly(libs.detekt.api)

    testImplementation(platform(libs.androidx.compose.bom))
    testImplementation(libs.androidx.ui) // Required for type resolution

    testImplementation(libs.detekt.test)
    testImplementation(libs.kotest)
    testImplementation(libs.jupiter)
}

java {
    withJavadocJar()
    withSourcesJar()
}

kotlin {
    jvmToolchain(17)
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
    systemProperty("junit.jupiter.testinstance.lifecycle.default", "per_class")
    systemProperty("compile-snippet-tests", project.hasProperty("compile-test-snippets"))
}