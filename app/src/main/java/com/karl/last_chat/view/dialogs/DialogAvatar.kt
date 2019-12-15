package com.karl.last_chat.view.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import com.karl.last_chat.R
import com.karl.last_chat.utils.DialogEnum
import com.karl.last_chat.utils.extensions.onClickViews
import kotlinx.android.synthetic.main.layout_dialog_avatar.*


class DialogAvatar(context: Context, private val listener: (dialogEnum: DialogEnum) -> Unit) :
    Dialog(context),
    View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imageCamera -> {
                listener(DialogEnum.CAMERA)
            }
            R.id.imagePic -> {
                listener(DialogEnum.IMAGE)
            }
            R.id.imageSee -> {

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_dialog_avatar)
        onClickViews(imageCamera, imagePic, imageSee)
    }
}
