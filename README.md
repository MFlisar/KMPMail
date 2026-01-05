[![Maven Central](https://img.shields.io/maven-central/v/io.github.mflisar.kmpmail/library?style=for-the-badge&color=blue)](https://central.sonatype.com/artifact/io.github.mflisar.kmpmail/library) ![API](https://img.shields.io/badge/api-23%2B-brightgreen.svg?style=for-the-badge) ![Kotlin](https://img.shields.io/github/languages/top/MFlisar/KMPMail.svg?style=for-the-badge&amp;color=blueviolet) ![Kotlin Multiplatform](https://img.shields.io/badge/Kotlin_Multiplatform-blue?style=for-the-badge&amp;label=Kotlin) [![License](https://img.shields.io/github/license/MFlisar/KMPMail?style=for-the-badge)](https://github.com/MFlisar/KMPMail/blob/main/LICENSE)
# KMPMail
![Android](https://img.shields.io/badge/android-3DDC84?style=for-the-badge) ![iOS](https://img.shields.io/badge/ios-A2AAAD?style=for-the-badge)

This library allows you to open the platforms **email chooser**.

- prefill the email subject
- prefill the email content
- attach files to the mail

# :information_source: Table of Contents

- [Supported Platforms](#computer-supported-platforms)
- [Setup](#wrench-setup)
- [Usage](#rocket-usage)
- [Demo](#sparkles-demo)
- [Other Libraries](#bulb-other-libraries)
- [API Documentation](#books-api-documentation)

# :computer: Supported Platforms

| Module | android | iOS |
|---|---|---|
| Library | ✅ | ✅ |

# :wrench: Setup

<details>

<summary>Dependencies</summary>

<br>

Simply add the dependencies inside your **build.gradle.kts** file.

```kotlin
val kmpmail = "<LATEST-VERSION>"

implementation("io.github.mflisar.kmpmail:library:${kmpmail}")
```

</details>

<details>

<summary>Version Catalogue</summary>

<br>

Define the dependencies inside your **libs.versions.toml** file.

```toml
[versions]

kmpmail = "<LATEST-VERSION>"

[libraries]

library = { module = "io.github.mflisar.kmpmail:library", version.ref = "kmpmail" }
```

And then use the definitions in your projects **build.gradle.kts** file like following:

```shell
implementation(libs.library)
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

# :sparkles: Demo

A full [demo](/demo) is included inside the demo module, it shows nearly every usage with working examples.

# :books: API Documentation

Check out the [API documentation](https://MFlisar.github.io/KMPMail/).

# :bulb: Other Libraries

You can find more libraries (all multiplatform) of mine that all do work together nicely [here](https://mflisar.github.io/Libraries/).
