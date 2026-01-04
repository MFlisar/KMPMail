package com.michaelflisar.kmpmail

/**
 * Represents an email to be sent
 *
 * @param receivers The email addresses of the receivers
 * @param subject The subject of the email
 * @param body The body of the email
 * @param bodyIsHtml Whether the body is in HTML format
 * @param attachments The files to be attached to the email
 */
class Mail(
    val receivers: List<String>,
    val subject: String,
    val body: String? = null,
    val bodyIsHtml: Boolean = false,
    val attachments: List<MailAttachmentFile> = emptyList(),
)

/**
 * Starts the email chooser
 *
 * @param chooserTitle The title of the chooser dialog
 *
 * @return true if the email chooser was started successfully, false otherwise
 */
expect fun Mail.startEmailChooser(chooserTitle: String) : Boolean