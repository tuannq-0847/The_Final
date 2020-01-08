package com.karl.last_chat.utils.extensions

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import com.karl.last_chat.view.dialogs.DialogOk
import com.karl.last_chat.view.dialogs.DialogWarning


fun Context.showMessage(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Context.showDialogWarning(message: String, actionOk: () -> Unit = {}) {
    val dialog = DialogWarning(this, message, actionOk)
    dialog.show()
}

fun Context.showDialogOk(message: String, actionOk: () -> Unit = {}) {
    val dialog = DialogOk(this, message, actionOk)
    dialog.show()
}

fun Context.getMimeType(uri: Uri): String? {
    var fileName: String? = ""
    if (uri.scheme.equals("file")) {
        fileName = uri.lastPathSegment
    } else {
        var cursor: Cursor? = null
        try {
            cursor = contentResolver.query(
                uri,
                arrayOf(MediaStore.Images.ImageColumns.DISPLAY_NAME),
                null,
                null,
                null
            )

            if (cursor != null && cursor.moveToFirst()) {
                fileName =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME))
            }
        } finally {

            if (cursor != null) {
                cursor.close()
            }
        }
    }
    return fileName
}

