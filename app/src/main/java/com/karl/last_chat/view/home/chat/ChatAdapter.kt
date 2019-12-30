package com.karl.last_chat.view.home.chat

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseRecyclerView
import com.karl.last_chat.data.model.Message
import kotlinx.android.synthetic.main.item_rec.view.*
import kotlinx.android.synthetic.main.item_send.view.*

class ChatAdapter(private val uid: String) :
    BaseRecyclerView<Message>(object : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem.content == oldItem.content
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

    override fun getItemViewType(position: Int): Int {
        val message = currentList[position]
        return if (message.idUserSend == uid) {
            8080
        } else {
            0
        }
    }

    override fun onBind(itemView: View, item: Message, position: Int) {
        Log.d("item", item.content)
        if (getItemViewType(position) == 0) {
            itemView.textRec.text = item.content
        } else {
            itemView.textSend.text = item.content
        }
    }
}
