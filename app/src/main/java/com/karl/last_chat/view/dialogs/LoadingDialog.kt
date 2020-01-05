package com.karl.last_chat.view.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseDialog

class LoadingDialog(context: Context) :
    Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_loading_rotate)
    }
}
