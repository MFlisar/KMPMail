package com.michaelflisar.kmpmail

import android.net.Uri
import kotlinx.io.files.Path
import java.io.File

actual class FeedbackFile internal constructor(
    val uri: Uri,
    val cacheFileName: String = uri.lastPathSegment ?: "file",
) {
    actual constructor(
        path: Path,
    ) : this(
        uri = Uri.fromFile(File(path.toString())),
        cacheFileName = path.name
    )

    constructor(
        file: File,
        cacheFileName: String = file.name,
    ) : this(Uri.fromFile(file), cacheFileName)

}