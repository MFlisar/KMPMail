package com.michaelflisar.kmpmail

import kotlinx.cinterop.ExperimentalForeignApi
import LibraryFramework.LibraryFrameworkObjC
import MailSenderHelper.MailSenderHelper

actual fun Feedback.startEmailChooser(
    chooserTitle: String,
) {
    val helper = MailSenderHelper()
    for (receivers in this.receivers) {
        helper.sendMailWithReceiver(
            receivers = receivers,
            //subject = this.subject,
            //body = this.text ?: "",
            //isBodyHtml = this.textIsHtml,
            attachments = this.attachments.map { it.path },
            //chooserTitle = chooserTitle
        )
    }
}

@ExperimentalForeignApi
actual fun test(): String {
    val api = LibraryFrameworkObjC()
    return api.test()
}