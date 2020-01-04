package com.karl.last_chat.view.home.message

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseRecyclerView
import com.karl.last_chat.data.model.LastMessage
import com.karl.last_chat.utils.extensions.loadWithGlide
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

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBind(itemView: View, item: LastMessage, position: Int) {
        itemView.run {
            if (item.seen == 0) {
                textLastMessage.setTextColor(
                    resources.getColor(
                        R.color.color_black,
                        resources.newTheme()
                    )
                )
            }
            textLastMessage.text = item.lastContent
            imageStatusUser.setImageResource(if (item.onlineStatus == 1) R.drawable.ic_online else R.drawable.ic_offline)
            imageUserLastMessage.loadWithGlide(item.pathImage)
            textUserRec.text = item.nameSender
        }
    }
}
