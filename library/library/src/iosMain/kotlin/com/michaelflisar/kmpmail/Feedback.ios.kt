package com.michaelflisar.kmpmail

actual fun Mail.startEmailChooser(
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