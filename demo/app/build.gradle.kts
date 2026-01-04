import com.codingfeline.buildkonfig.compiler.FieldSpec.Type
import com.michaelflisar.kmpdevtools.BuildFileUtil
import com.michaelflisar.kmpdevtools.Targets
import com.michaelflisar.kmpdevtools.config.AppModuleData
import com.michaelflisar.kmpdevtools.config.sub.AndroidAppConfig
import com.michaelflisar.kmpdevtools.config.sub.DesktopAppConfig
import com.michaelflisar.kmpdevtools.config.sub.WasmAppConfig
import com.michaelflisar.kmpdevtools.core.configs.Config
import com.michaelflisar.kmpdevtools.core.configs.LibraryConfig
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.hotreload)
    alias(libs.plugins.buildkonfig)
    alias(deps.plugins.kmpdevtools.buildplugin)
}

// ------------------------
// Setup
// ------------------------

val config = Config.read(rootProject)
val libraryConfig = LibraryConfig.read(rootProject)

val buildTargets = Targets(
    // mobile
    android = true,
    iOS = true,
    // desktop
    windows = false,
    macOS = false,
    // web
    wasm = false
)

val androidConfig = AndroidAppConfig(
    compileSdk = app.versions.compileSdk,
    minSdk = app.versions.minSdk,
    targetSdk = app.versions.targetSdk
)

val appModuleData = AppModuleData(
    project = project,
    config = config,
    appName = "${libraryConfig.library.name} Demo",
    namespace = "com.michaelflisar.demo",
    versionName = "1.0.0",
    versionCode = 1,
    androidConfig = androidConfig,
    desktopConfig = null,
    wasmConfig = null
)

// ------------------------
// Kotlin
// ------------------------

buildkonfig {
    packageName = appModuleData.namespace
    defaultConfigs {
        buildConfigField(Type.STRING, "versionName", appModuleData.versionName)
        buildConfigField(Type.INT, "versionCode", appModuleData.versionCode.toString())
        buildConfigField(Type.STRING, "packageName", appModuleData.namespace)
        buildConfigField(Type.STRING, "appName", appModuleData.appName)
    }
}

kotlin {

    //-------------
    // Targets
    //-------------

    buildTargets.setupTargetsApp(appModuleData)

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    // ------------------------
    // Source Sets
    // ------------------------

    sourceSets {

        // ---------------------
        // custom source sets
        // ---------------------

        val commonMain by getting
        val iosMain by creating { dependsOn(commonMain) }

        buildTargets.setupDependencies(
            iosMain,
            sourceSets,
            buildTargets,
            listOf(com.michaelflisar.kmpdevtools.core.Platform.IOS)
        )

        // ------------------------
        // dependencies
        // ------------------------

        commonMain.dependencies {

            // resources
            //implementation(compose.components.resources)

            // Modules
            implementation(project(":demo:shared"))
        }

        androidMain.dependencies {

            // AndroidX/Compose/Material
            implementation(libs.androidx.activity.compose)

        }

        jvmMain.dependencies {

            implementation(compose.desktop.currentOs) {
                exclude(group = "org.jetbrains.compose.material", module = "material")
            }

        }
    }
}

// -------------------
// Configurations
// -------------------

// android configuration
android {

    BuildFileUtil.setupAndroidApp(
        appModuleData = appModuleData,
        buildConfig = true,
        generateResAppName = true,
        checkDebugKeyStoreProperty = true,
        setupBuildTypesDebugAndRelease = true
    )
}