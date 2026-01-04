package com.michaelflisar.kmpmail

import kotlinx.io.files.Path

actual class FeedbackFile(
    val path: String,
) {
    actual constructor(path: Path) : this(path.toString())
}