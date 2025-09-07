import com.android.build.api.dsl.*
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

private typealias AndroidExtensions = CommonExtension<
        out BuildFeatures,
        out BuildType,
        out DefaultConfig,
        out ProductFlavor,
        out AndroidResources,
        out Installation>


private val Project.androidExtension: AndroidExtensions
    get() = extensions.findByType(BaseAppModuleExtension::class)
        ?: extensions.findByType(LibraryExtension::class)
        ?: error(
            "Project $name is not an Android module and does not have the Android App or Library plugin applied."
        )

fun Project.androidConfig(block: AndroidExtensions.() -> Unit) {
    block(androidExtension)
}

fun Project.kotlin(block: KotlinAndroidProjectExtension.() -> Unit) {
    block(the())
}

val Project.libs: LibrariesForLibs
    get() = the<LibrariesForLibs>()