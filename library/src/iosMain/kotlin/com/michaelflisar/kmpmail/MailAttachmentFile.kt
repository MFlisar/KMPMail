package com.michaelflisar.kmpmail

import kotlinx.io.files.Path

/**
 * Represents a file to be attached to an email
 *
 * @param path The path to the file
 */
actual class MailAttachmentFile(
    val path: String,
) {
    actual constructor(path: Path) : this(path.toString())
}