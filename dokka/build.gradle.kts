import com.michaelflisar.kmpdevtools.core.configs.Config
import com.michaelflisar.kmpdevtools.core.configs.LibraryConfig

plugins {
    kotlin("jvm") apply false
    alias(libs.plugins.dokka)
    alias(deps.plugins.kmpdevtools.buildplugin)
}

dependencies {
    dokka(project(":kmpmail:library"))
}

dokka {

    val config = Config.read(rootProject)
    val libraryConfig = LibraryConfig.read(rootProject)

    moduleName.set(libraryConfig.library.name)

    //dokkaPublications.html {
    //    includes.from("DocsModule.md")
    //}
}