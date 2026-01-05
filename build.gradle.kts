import com.michaelflisar.kmpdevtools.BuildFilePlugin

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.compose) apply false
    alias(libs.plugins.compose.hotreload) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.gradle.maven.publish.plugin) apply false
    alias(deps.plugins.kmpdevtools.buildplugin) // apply false
}

// exclude all demo projects from CI builds
subprojects {
    if (project.path.contains(":demo:", ignoreCase = true) && System.getenv("CI") == "true") {
        tasks.configureEach {
            enabled = false
        }
    }
}
// ----------------------------
// Apply custom build file plugin
// ----------------------------


buildFilePlugin {

    // do not build demo projects in CI
    excludeDemoFromCI.set(true)
}