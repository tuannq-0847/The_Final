package com.karl.last_chat.view.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseDialog
import com.karl.last_chat.utils.extensions.showMessage
import kotlinx.android.synthetic.main.layout_forgot_password.*

class ForgotPwDialog(context: Context, private val listener: (email: String) -> Unit) :
    BaseDialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_forgot_password)
        btnCancelD.setOnClickListener {
            dismiss()
        }
        btnEmailMelD.setOnClickListener {
            if (edtEmail.text.toString().isNotEmpty())
                listener(edtEmail.text.toString())
            else context.showMessage(context.getString(R.string.not_type))
        }
    }
}
