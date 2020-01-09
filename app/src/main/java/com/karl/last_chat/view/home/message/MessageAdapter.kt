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

class MessageAdapter(private val uid: String, listener: (item: LastMessage) -> Unit) :
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
            if (item.seen != uid && item.seen != "both") {
                textLastMessage.setTextColor(
                    resources.getColor(
                        R.color.color_black,
                        resources.newTheme()
                    )
                )
            }
            textNewFriend.visibility = if (item.type == "new") View.VISIBLE else View.GONE
            when (item.type) {
                "text" -> textLastMessage.text = item.lastContent
                "image" -> {
                    if (uid == item.idUserSend) {
                        textLastMessage.text = "You sent a image"
                    } else textLastMessage.text = "${item.nameSender} sent a image"
                }
                "file" -> {
                    if (uid == item.idUserSend) {
                        textLastMessage.text = "You sent a file"
                    } else textLastMessage.text = "${item.nameSender} sent a file"
                }
                "new" -> {
                    textLastMessage.text = item.lastContent
                }
                else -> {

                }
            }
            imageStatusUser.setImageResource(if (item.onlineStatus == 1) R.drawable.ic_online else R.drawable.ic_offline)
            imageUserLastMessage.loadWithGlide(item.pathImage)
            textUserRec.text = item.nameSender
        }
    }
}
