plugins {
    kotlin("jvm") apply false
    alias(libs.plugins.dokka)
}

dependencies {
    dokka(project(":kmpmail:library"))
}

dokka {
    moduleName.set("Dokka MultiModule Example")

    //dokkaPublications.html {
    //    includes.from("DocsModule.md")
    //}
}