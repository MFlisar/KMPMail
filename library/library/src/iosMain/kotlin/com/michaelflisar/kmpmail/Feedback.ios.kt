package com.michaelflisar.kmpmail

import kotlinx.cinterop.ExperimentalForeignApi
import LibraryFramework.LibraryFrameworkObjC
import LibraryFramework.MailSenderHelper

actual fun Feedback.startEmailChooser(
    chooserTitle: String,
) {
    startEmailChooserImpl(this, chooserTitle)
}

@OptIn(ExperimentalForeignApi::class)
actual fun test(): String {
    val api = LibraryFrameworkObjC()
    return api.test()
}

@OptIn(ExperimentalForeignApi::class)
internal actual fun startEmailChooserImpl(
    feedback: Feedback,
    chooserTitle: String,
) {
    val helper = MailSenderHelper()
    for (receiver in feedback.receivers) {
        helper.sendMailWithReceiver(
            receiver = receiver,
            //subject = this.subject,
            //body = this.text ?: "",
            //isBodyHtml = this.textIsHtml,
            attachments = feedback.attachments.map { it.path },
            //chooserTitle = chooserTitle
        )
    }
}