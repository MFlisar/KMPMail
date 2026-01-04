import com.michaelflisar.kmpdevtools.BuildFileUtil
import com.michaelflisar.kmpdevtools.Targets
import com.michaelflisar.kmpdevtools.config.LibraryModuleData
import com.michaelflisar.kmpdevtools.config.sub.AndroidLibraryConfig
import com.michaelflisar.kmpdevtools.core.Platform
import com.michaelflisar.kmpdevtools.core.configs.Config
import com.michaelflisar.kmpdevtools.core.configs.LibraryConfig
import org.gradle.kotlin.dsl.add
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import org.jetbrains.kotlin.konan.target.KonanTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.dokka)
    alias(libs.plugins.gradle.maven.publish.plugin)
    alias(libs.plugins.binary.compatibility.validator)
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

val androidConfig = AndroidLibraryConfig(
    compileSdk = app.versions.compileSdk,
    minSdk = app.versions.minSdk
)

val libraryModuleData = LibraryModuleData(
    project = project,
    config = config,
    libraryConfig = libraryConfig,
    androidConfig = androidConfig
)

// -------------------
// Setup
// -------------------

kotlin {

    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    //-------------
    // Targets
    //-------------

    buildTargets.setupTargetsLibrary(libraryModuleData)

    // XCFrameworks
    // Expected folders in iosXCFramework: [cinterop, Headers, <frameworkName>.xcframework]
    // ATTENTION: new frameworks must be added to the iosApp as dependecies!
    //buildTargets.setupXCFramework(project, frameworkName = "LibraryFramework")

    fun sliceDirFor(target: KonanTarget): String = when (target) {
        KonanTarget.IOS_ARM64 -> "ios-arm64"
        KonanTarget.IOS_X64 -> "ios-x86_64-simulator"
        KonanTarget.IOS_SIMULATOR_ARM64 -> "ios-arm64_x86_64-simulator"
        else -> error("Unsupported target: $target")
    }
    val frameworkName = "LibraryFramework"
    val folderCInterop: File = project.file("iosXCFramework/cinterop")
    val folderXCFramework: File = project.file("iosXCFramework/${frameworkName}.xcframework")
    val relativeHeadersFolderInXCFramework = "Headers"
    val xcf = XCFramework("LibraryFramework")
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.compilations.getByName("main") {

            cinterops.create(frameworkName) {

                // 1) add all .def files in the cinterop folder
                val defFiles =
                    folderCInterop.listFiles { file: File -> file.extension == "def" }
                defFiles.forEach { defFile(it) }

                // 2) include header dirs (cinterop + provided Headers folder)
                val sliceDir =
                    folderXCFramework.resolve(sliceDirFor(iosTarget.konanTarget))
                includeDirs.allHeaders(
                    sliceDir.resolve("$frameworkName.framework/$relativeHeadersFolderInXCFramework"),
                    folderCInterop
                )

                // 3) compiler options
                compilerOpts(
                    "-F", sliceDir.absolutePath,
                    "-framework", frameworkName
                )
            }
        }
        iosTarget.binaries.framework {
            baseName ="LibraryFramework"
            isStatic = true
            xcf.add(this)
        }
    }

    // -------
    // Sources
    // -------

    sourceSets {

        // ---------------------
        // custom source sets
        // ---------------------

        val iosMain by creating { dependsOn(commonMain.get()) }

        buildTargets.setupDependencies(iosMain, sourceSets, buildTargets, listOf(Platform.IOS))

        // ---------------------
        // dependencies
        // ---------------------

        commonMain.dependencies {

            // Kotlin
            api(deps.kotlinx.io.core)

        }

        androidMain.dependencies {

            implementation(libs.androidx.core)

            implementation(deps.cachefileprovider)

        }
    }
}

// -------------------
// Publish
// -------------------

// maven publish configuration
if (BuildFileUtil.checkGradleProperty(project, "publishToMaven") != false)
    BuildFileUtil.setupMavenPublish(project, config, libraryConfig)