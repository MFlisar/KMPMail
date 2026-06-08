[![Maven Central](https://img.shields.io/maven-central/v/io.github.mflisar.kmpmail/library?style=for-the-badge&color=blue)](https://central.sonatype.com/artifact/io.github.mflisar.kmpmail/library) ![API](https://img.shields.io/badge/api-24%2B-brightgreen.svg?style=for-the-badge) ![Kotlin](https://img.shields.io/github/languages/top/MFlisar/KMPMail.svg?style=for-the-badge&amp;color=blueviolet) ![Kotlin Multiplatform](https://img.shields.io/badge/Kotlin_Multiplatform-blue?style=for-the-badge&amp;label=Kotlin)
# KMPMail
![Platforms](https://img.shields.io/badge/PLATFORMS-black?style=for-the-badge) ![Android](https://img.shields.io/badge/android-3DDC84?style=for-the-badge) ![iOS](https://img.shields.io/badge/ios-A2AAAD?style=for-the-badge)

This library allows you to open the platforms **email chooser**.

It provides following main features:

- prefill the email subject
- prefill the email content
- attach files to the mail

# Table of Contents

- [Supported Platforms](#computer-supported-platforms)
- [Versions](#arrow_right-versions)
- [Setup](#wrench-setup)
- [Usage](#rocket-usage)
- [Demo](#sparkles-demo)
- [API](#books-api)
- [Other Libraries](#bulb-other-libraries)

# :computer: Supported Platforms

| Module | android | iOS | Notes |
|---|---|---|---|
| library | ✅ | ✅ | This is a very small library that allows you to send feedback from an app without internet permission via email, either directly or via an unintrusive notification. |

# :arrow_right: Versions

| Dependency | Version |
|---|---|
| Kotlin | `2.4.0` |
| Jetbrains Compose | `1.11.1` |
| Jetbrains Compose Material3 | `1.9.0` |

# :wrench: Setup

<details open>

<summary><b>Using Version Catalogs</b></summary>

<br>

Define the dependencies inside your **libs.versions.toml** file.

```toml
[versions]

kmpmail = "<LATEST-VERSION>"

[libraries]

kmpmail-library = { module = "io.github.mflisar.kmpmail:library", version.ref = "kmpmail" }
```

And then use the definitions in your projects **build.gradle.kts** file like following:

```java
implementation(libs.kmpmail.library)
```

</details>

<details>

<summary><b>Direct Dependency Notation</b></summary>

<br>

Simply add the dependencies inside your **build.gradle.kts** file.

```kotlin
val kmpmail = "<LATEST-VERSION>"

implementation("io.github.mflisar.kmpmail:library:${kmpmail}")
```

</details>

# :rocket: Usage

This library is used like following:

<!-- snippet: mail -->
```kt
val mail = Mail(
    receivers = listOf(receiver),
    subject = "Feedback from $platform Demo App",
    body = "Please write your feedback here...\n\n",
    bodyIsHtml = false,
    attachments = listOf(MailAttachmentFile(tempFile))
)
val success = mail.startEmailChooser("Select email app")
```
<!-- endSnippet -->

This will start the email chooser of the platform. If it returns `false`, no email clients are installed or set up, so you could fallback to a `mailto:` solution or whatever you want.

# :sparkles: Demo

A full [demo](/demo) is included inside the demo module, it shows nearly every usage with working examples.

# :books: API

Check out the [API documentation](https://MFlisar.github.io/KMPMail/).

# :bulb: Other Libraries

You can find more libraries (all multiplatform) of mine that all do work together nicely [here](https://mflisar.github.io/Libraries/).
