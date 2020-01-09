package com.karl.last_chat.view.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseDialog
import com.karl.last_chat.utils.extensions.onClickViews
import kotlinx.android.synthetic.main.layout_setting_personal.*

class DialogSetting(
    context: Context,
    private val title: String,
    private val listener: (isLogout: Boolean) -> Unit
) :
    BaseDialog(context), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.textEditProfile -> {
                listener(false)
                dismiss()
            }
            R.id.textLogout -> {
                listener(true)
                dismiss()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_setting_personal)
        onClickViews(textEditProfile, textLogout)
    }
}
