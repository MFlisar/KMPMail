import com.michaelflisar.kmpdevtools.core.configs.LibraryConfig

plugins {
    kotlin("jvm") apply false
    alias(libs.plugins.dokka)
}

dependencies {
    dokka(project(":kmpmail:library"))
}

dokka {
    val libraryConfig = LibraryConfig.read(rootProject)
    moduleName.set(libraryConfig.library.name)
}
