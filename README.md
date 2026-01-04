[![Maven Central](https://img.shields.io/maven-central/v/io.github.mflisar.librarytemplate/core?style=for-the-badge&color=blue)](https://central.sonatype.com/artifact/io.github.mflisar.librarytemplate/core) ![API](https://img.shields.io/badge/api-23%2B-brightgreen.svg?style=for-the-badge) ![Kotlin](https://img.shields.io/github/languages/top/MFlisar/LibraryTemplate.svg?style=for-the-badge&amp;color=blueviolet) ![Kotlin Multiplatform](https://img.shields.io/badge/Kotlin_Multiplatform-blue?style=for-the-badge&amp;label=Kotlin) [![License](https://img.shields.io/github/license/MFlisar/LibraryTemplate?style=for-the-badge)](https://github.com/MFlisar/LibraryTemplate/blob/master/LICENSE)
# LibraryTemplate
![Android](https://img.shields.io/badge/android-3DDC84?style=for-the-badge) ![iOS](https://img.shields.io/badge/ios-A2AAAD?style=for-the-badge) ![Windows](https://img.shields.io/badge/windows-5382A1?style=for-the-badge) ![macOS](https://img.shields.io/badge/macos-B0B0B0?style=for-the-badge) ![WebAssembly](https://img.shields.io/badge/wasm-624DE7?style=for-the-badge)

This is a **full template library**.

- Feature 1
- Feature 2
- ...

# :information_source: Table of Contents

- [Screenshots](#camera-screenshots)
- [Supported Platforms](#computer-supported-platforms)
- [Setup](#wrench-setup)
- [Usage](#page_facing_up-usage)
- [Modules](#file_folder-modules)
- [Demo](#diamonds-demo)
- [More](#information_source-more)

# :camera: Screenshots

![kotlin](documentation/screenshots/kotlin.png)

# :computer: Supported Platforms

| Module | android | iOS | windows | macOS | wasm |
|---|---|---|---|---|---|
| Core | ✅ | ✅ | ✅ | ✅ | ✅ |
| Module 1 | ✅ | ✅ | ✅ | ✅ | ✅ |
| Module 2 | ✅ | ✅ | ✅ | ✅ | ✅ |

# :wrench: Setup

<details>

<summary>Dependencies</summary>

<br>

Simply add the dependencies inside your **build.gradle.kts** file.

```kotlin
val librarytemplate = "<LATEST-VERSION>"

implementation("io.github.mflisar.librarytemplate:core:${librarytemplate}")
implementation("io.github.mflisar.librarytemplate:modules-module1:${librarytemplate}")
implementation("io.github.mflisar.librarytemplate:modules-module2:${librarytemplate}")
```

</details>

<details>

<summary>Version Catalogue</summary>

<br>

Define the dependencies inside your **libs.versions.toml** file.

```toml
[versions]

librarytemplate = "<LATEST-VERSION>"

[libraries]

core = { module = "io.github.mflisar.librarytemplate:core", version.ref = "librarytemplate" }
modules.module1 = { module = "io.github.mflisar.librarytemplate:modules-module1", version.ref = "librarytemplate" }
modules.module2 = { module = "io.github.mflisar.librarytemplate:modules-module2", version.ref = "librarytemplate" }
```

And then use the definitions in your projects **build.gradle.kts** file like following:

```shell
implementation(libs.core)
implementation(libs.modules.module1)
implementation(libs.modules.module2)
```

</details>

# :page_facing_up: Usage

This library is used like following:

...

# :file_folder: Modules

- [Module 1](documentation/modules/module1.md)

# :diamonds: Demo

A full [demo](/demo) is included inside the demo module, it shows nearly every usage with working examples.

# :information_source: More

- [Information 1](documentation/infos/info1.md)
- [Information 2](documentation/infos/info2.md)
