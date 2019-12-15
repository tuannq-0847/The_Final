package com.karl.last_chat.view.home.chat

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseRecyclerView
import com.karl.last_chat.data.remote.Message

class ChatAdapter(callBack: DiffUtil.ItemCallback<Message>) :
    BaseRecyclerView<Message>(callBack) {
    override fun getLayoutRes(viewType: Int): Int =
        when (viewType) {
            0 -> R.layout.item_rec
            else -> R.layout.item_send
        }

    override fun onBind(itemView: View, item: Message) {

    }
}
