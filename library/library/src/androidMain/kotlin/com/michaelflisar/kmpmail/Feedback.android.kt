package com.michaelflisar.kmpmail

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.text.HtmlCompat
import com.michaelflisar.cachefileprovider.CachedFileProvider

actual fun Feedback.startEmailChooser(
    chooserTitle: String,
): Boolean {
    val intent = buildIntent(
        context = AppContextProvider.context,
        feedback = this,
        chooserTitle = chooserTitle
    )
    try {
        AppContextProvider.context.startActivity(intent)
    } catch (e: Exception) {
        if (e is android.content.ActivityNotFoundException) {
            return false
        } else {
            throw e
        }
    }
    return true
}

private fun buildIntent(
    context: Context,
    feedback: Feedback,
    chooserTitle: String,
): Intent {
    val single = feedback.attachments.size == 1
    val intent = Intent(if (single) Intent.ACTION_SEND else Intent.ACTION_SEND_MULTIPLE)
    intent.type = if (single) "message/rfc822" else "text/plain"
    intent.putExtra(Intent.EXTRA_EMAIL, feedback.receivers.toTypedArray())
    intent.putExtra(Intent.EXTRA_SUBJECT, feedback.subject)
    if (feedback.body != null && feedback.body.isNotEmpty()) {
        intent.putExtra(
            /* name = */ Intent.EXTRA_TEXT,
            /* value = */
            if (feedback.bodyIsHtml) HtmlCompat.fromHtml(
                feedback.body,
                HtmlCompat.FROM_HTML_MODE_LEGACY
            ) else feedback.body
        )
    }

    if (feedback.attachments.size == 1) {
        val attachment = feedback.attachments[0]
        val uri = copyToCache(context, attachment)
        intent.putExtra(Intent.EXTRA_STREAM, uri)
    } else if (feedback.attachments.size > 1) {
        val uris = ArrayList<Uri>()
        for (i in feedback.attachments.indices) {
            val attachment = feedback.attachments[i]
            val uri = copyToCache(context, attachment)
            uris.add(uri)
        }
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris)
    }

    var flags = Intent.FLAG_ACTIVITY_NEW_TASK
    if (feedback.attachments.isNotEmpty())
        flags = flags or Intent.FLAG_GRANT_READ_URI_PERMISSION

    intent.flags = flags
    return Intent.createChooser(intent, chooserTitle).apply {
        this.flags = flags
    }
}

private fun copyToCache(context: Context, file: FeedbackFile): Uri {
    // 1) copy input file to cache file => return a simple file (not shareable!)
    val cacheFile = CachedFileProvider.copyFileToCache(
        context,
        file.uri,
        file.cacheFileName
    )

    // 2) return uri for cache file (shareable via FileProvider)
    return CachedFileProvider.getCacheFileUri(context, file.cacheFileName)
}