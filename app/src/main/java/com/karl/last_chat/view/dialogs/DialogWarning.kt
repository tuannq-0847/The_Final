package com.karl.last_chat.view.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseDialog
import kotlinx.android.synthetic.main.layout_warning_dialog.*

class DialogWarning(context: Context, private val title: String, private val listener: () -> Unit) :
    BaseDialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_warning_dialog)
        titleContent.text = title
        textOk.setOnClickListener {
            listener()
            dismiss()
        }
    }
}
