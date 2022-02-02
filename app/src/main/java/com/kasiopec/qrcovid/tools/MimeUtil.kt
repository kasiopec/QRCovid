package com.kasiopec.qrcovid.tools

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase

object MimeUtil {
    fun Uri.getMimeType(context: Context): String? {
        return when (scheme) {
            ContentResolver.SCHEME_CONTENT -> context.contentResolver.getType(this)
            ContentResolver.SCHEME_FILE -> MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                MimeTypeMap.getFileExtensionFromUrl(toString()).toLowerCase(Locale.current)
            )
            else -> null
        }
    }

    val mimeArray : Array<String>
        get() = arrayOf(
            "application/octet-stream",
            "image/*"
        )
}