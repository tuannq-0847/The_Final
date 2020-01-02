package com.karl.last_chat.view.home.message

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseRecyclerView
import com.karl.last_chat.data.model.LastMessage
import com.karl.last_chat.utils.extensions.loadWithGlide
import kotlinx.android.synthetic.main.item_message.view.*
import kotlinx.android.synthetic.main.items_friends.view.*

class MessageAdapter(listener: (item: LastMessage) -> Unit) :
    BaseRecyclerView<LastMessage>(object : DiffUtil.ItemCallback<LastMessage>() {
        override fun areItemsTheSame(oldItem: LastMessage, newItem: LastMessage): Boolean {
            return oldItem.idLast == newItem.idLast
        }

        override fun areContentsTheSame(oldItem: LastMessage, newItem: LastMessage): Boolean {
            return oldItem == newItem
        }

    }, listener) {

    override fun getLayoutRes(viewType: Int): Int = R.layout.items_friends

    override fun onBind(itemView: View, item: LastMessage, position: Int) {
        itemView.run {
            textLastMesasge.text = item.lastContent
            imageStatusUser.setImageResource(if (item.onlineStatus == 1) R.drawable.ic_online else R.drawable.ic_offline)
            imageUserLastMessage.loadWithGlide(item.pathImage)
        }
    }
}
