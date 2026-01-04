package com.michaelflisar.kmpmail

import platform.Foundation.NSData
import platform.Foundation.NSURL
import platform.Foundation.dataWithContentsOfURL
import platform.MessageUI.MFMailComposeViewController
import platform.UIKit.UIApplication

object MailChooser {

    fun sendMail(
        receivers: List<String>,
        subject: String,
        body: String,
        isHTML: Boolean,
        attachments: List<String>,
    ): Boolean {
        if (!MFMailComposeViewController.canSendMail()) {
            println("Mail services are not available")
            return false
        }

        val mailVC = MFMailComposeViewController().apply {
            setToRecipients(receivers)
            setSubject(subject)
            setMessageBody(body, isHTML)
            attachments.forEach {
                val url = NSURL.fileURLWithPath(it)
                val data = NSData.dataWithContentsOfURL(url)
                val filename = url.lastPathComponent ?: "attachment"
                val ext = url.pathExtension?.lowercase() ?: ""
                val mime = mimeTypeForExtension(ext)
                if (data != null) {
                    addAttachmentData(data, mimeType = mime, fileName = filename)
                }
            }
        }

        val rootViewController = UIApplication.sharedApplication.keyWindow?.rootViewController
        if (rootViewController == null) {
            println("Could not find root view controller")
            return false
        }
        rootViewController.presentViewController(mailVC, animated = true, completion = null)
        return true
    }

    private fun mimeTypeForExtension(fileExtension: String): String {
        val ext = fileExtension.trim().lowercase()
        // seems to be irrelevant for the mail client and we can't get a proper mapping here easily anyways
        return "application/octet-stream"
    }

}