package com.michaelflisar.kmpmail

actual fun Feedback.startEmailChooser(
    chooserTitle: String,
): Boolean {
    return MailChooser.sendMail(
        receivers = receivers,
        subject = subject,
        body = body ?: "",
        isHTML = bodyIsHtml,
        attachments = attachments.map { it.path }
    )
}