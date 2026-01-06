package com.michaelflisar.kmpmail

import android.annotation.SuppressLint
import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri

/**
 * A ContentProvider to provide a global application context
 */
class AppContextProvider : ContentProvider() {

    companion object {

        /**
         * The application context
         */
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate(): Boolean {
        AppContextProvider.context = context!!.applicationContext
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?,
    ): Cursor? = null

    override fun getType(uri: Uri): String? = null

    override fun insert(uri: Uri, values: ContentValues?): Uri? = null

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int = 0

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?,
    ): Int = 0
}