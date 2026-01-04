package com.michaelflisar.kmpmail

import kotlinx.io.files.Path

expect class FeedbackFile {
    constructor(path: Path)
}