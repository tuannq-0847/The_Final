package com.karl.last_chat.view.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import com.karl.last_chat.R
import kotlinx.android.synthetic.main.layout_forgot_password.*

class ForgotPwDialog(context: Context, private val listener: () -> Unit) :
    Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_forgot_password)
        btnCancelD.setOnClickListener {
            dismiss()
        }
    }

    override fun onStart() {
        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        super.onStart()
    }
}
