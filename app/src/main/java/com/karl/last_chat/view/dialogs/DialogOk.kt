package com.karl.last_chat.view.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.karl.last_chat.R
import kotlinx.android.synthetic.main.layout_warning_dialog.*

class DialogOk(context: Context, private val title: String, private val listener: () -> Unit) :
    Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_ok_dialog)
        titleContent.text = title
        textOk.setOnClickListener {
            listener()
            dismiss()
        }
    }
}
