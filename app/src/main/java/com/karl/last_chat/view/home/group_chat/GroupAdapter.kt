package com.karl.last_chat.view.home.group_chat

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseRecyclerView
import com.karl.last_chat.data.model.Message

class GroupAdapter :
    BaseRecyclerView<Message>(object : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem.idMessage == newItem.idMessage
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }

    }) {
    override fun getLayoutRes(viewType: Int): Int =
        when (viewType) {
            0 -> R.layout.item_rec
            else -> R.layout.item_send
        }

    override fun onBind(itemView: View, item: Message) {

    }
}