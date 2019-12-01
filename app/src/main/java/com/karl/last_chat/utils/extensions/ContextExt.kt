package com.karl.last_chat.utils.extensions

import android.content.Context
import android.widget.Toast
import com.karl.last_chat.view.dialogs.DialogWarning

fun Context.showMessage(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Context.showDialogWarning(message: String, actionOk: () -> Unit={}) {
    val dialog = DialogWarning(this, message, actionOk)
    dialog.show()
}