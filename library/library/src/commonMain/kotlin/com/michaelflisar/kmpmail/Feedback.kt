package com.michaelflisar.kmpmail

class Feedback(
    val receivers: List<String>,
    val subject: String,
    val body: String? = null,
    val bodyIsHtml: Boolean = false,
    val attachments: List<FeedbackFile> = emptyList(),
)

/**
 * Starts the email chooser
 *
 * @param chooserTitle The title of the chooser dialog
 *
 * @return true if the email chooser was started successfully, false otherwise
 */
expect fun Feedback.startEmailChooser(chooserTitle: String) : Boolean