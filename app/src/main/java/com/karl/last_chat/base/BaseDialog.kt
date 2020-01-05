package com.karl.last_chat.base

import android.app.Dialog
import android.content.Context
import android.view.WindowManager

abstract class BaseDialog(context: Context) : Dialog(context) {

    override fun onStart() {
        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        super.onStart()
    }
}