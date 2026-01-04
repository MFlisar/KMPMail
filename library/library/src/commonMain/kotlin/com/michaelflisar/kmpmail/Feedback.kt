package com.michaelflisar.kmpmail

class Feedback(
    val receivers: List<String>,
    val subject: String,
    val text: String? = null,
    val textIsHtml: Boolean = false,
    val attachments: List<FeedbackFile> = emptyList(),
)

expect fun test() : String

fun executeTest() : String {
    return test()
}

/**
 * Starts the email chooser
 *
 * @param chooserTitle The title of the chooser dialog
 */
expect fun Feedback.startEmailChooser(chooserTitle: String)