package com.michaelflisar.kmpmail

import kotlinx.io.files.Path

/**
 * Represents a file to be attached to an email
 */
expect class MailAttachmentFile {
    /**
     * Creates a new MailAttachmentFile with the given path
     *
     * @param path The path to the file
     */
    constructor(path: Path)
}