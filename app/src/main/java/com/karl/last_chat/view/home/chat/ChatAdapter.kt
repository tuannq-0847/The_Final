package com.karl.last_chat.view.home.chat

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseRecyclerView
import com.karl.last_chat.data.model.Message
import com.karl.last_chat.utils.extensions.loadWithGlide
import kotlinx.android.synthetic.main.item_chat_file_rec.view.*
import kotlinx.android.synthetic.main.item_chat_file_rec.view.textNameFile
import kotlinx.android.synthetic.main.item_chat_file_send.view.*
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
    private var pathAvatar = ""

    override fun getLayoutRes(viewType: Int): Int =
        when (viewType) {
            0 -> R.layout.item_rec
            1 -> R.layout.item_send
            2 -> R.layout.item_chat_file_rec
            3 -> R.layout.item_chat_file_send
            4 -> R.layout.item_chat_image_rec
            5 -> R.layout.item_chat_image_send
            else -> R.layout.item_rec
        }

    override fun getItemViewType(position: Int): Int {
        val message = currentList[position]
        return when {
            message.idUserSend == uid && message.type == "text" -> 1
            message.idUserSend != uid && message.type == "text" -> 0
            message.idUserSend == uid && message.type == "file" -> 3
            message.idUserSend != uid && message.type == "file" -> 2
            message.idUserSend == uid && message.type == "image" -> 5
            message.idUserSend != uid && message.type == "image" -> 4
            else -> 6
        }
    }

    fun setAvatar(url: String) {
        pathAvatar = url
    }

    override fun onBind(itemView: View, item: Message, position: Int) {
        itemView.run {
            when (getItemViewType(position)) {
                0 -> {
                    textRec.text = item.content
                    if (pathAvatar.isNotEmpty()) itemView.imageAvatarUserReceiver.loadWithGlide(
                        pathAvatar
                    )
                }
                1 -> {
                    textSend.text = item.content
                }
                2 -> {
                    textNameFile.text = item.content
                    if (pathAvatar.isNotEmpty()) itemView.imageAvatarRec.loadWithGlide(pathAvatar)
                }
                3 -> {
                    textNameFile.text = item.content
                }
                4 -> {
                    textNameFileSend.text = item.content
                }
                5 -> {

                }
                else ->
            }
        }
    }
}
