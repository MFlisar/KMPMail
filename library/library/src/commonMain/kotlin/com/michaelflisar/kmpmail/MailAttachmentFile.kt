package com.michaelflisar.kmpmail

import kotlinx.io.files.Path

/**
 * Represents a file to be attached to an email
 *
 * @param path The path to the file
 */
expect class MailAttachmentFile {
    constructor(path: Path)
}