package com.karl.last_chat.view.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import com.karl.last_chat.R
import com.karl.last_chat.utils.extensions.onClickViews
import kotlinx.android.synthetic.main.layout_setting_personal.*

class DialogSetting(context: Context, private val title: String, private val listener: () -> Unit) :
    Dialog(context), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.textEditProfile -> {
                //todo
            }
            R.id.textLogout -> {
                listener()
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
