package com.karl.last_chat.view.home.chat

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseRecyclerView
import com.karl.last_chat.data.model.EnumListener
import com.karl.last_chat.data.model.Message
import com.karl.last_chat.utils.extensions.loadWithGlide
import kotlinx.android.synthetic.main.item_chat_file_rec.view.*
import kotlinx.android.synthetic.main.item_chat_file_send.view.*
import kotlinx.android.synthetic.main.item_chat_image_rec.view.*
import kotlinx.android.synthetic.main.item_chat_image_send.view.*
import kotlinx.android.synthetic.main.item_rec.view.*
import kotlinx.android.synthetic.main.item_send.view.*

class ChatAdapter(
    private val uid: String,
    private val listener: (data: Message, enumListener: EnumListener) -> Unit
) :
    BaseRecyclerView<Message>(object : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem.content == oldItem.content
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }

    }) {
    private var pathAvatar = ""

    override fun getLayoutRes(viewType: Int): Int {
        Log.d("viewType", viewType.toString())
        return when (viewType) {
            0 -> R.layout.item_rec
            1 -> R.layout.item_send
            2 -> R.layout.item_chat_file_rec
            3 -> R.layout.item_chat_file_send
            4 -> R.layout.item_chat_image_rec
            5 -> R.layout.item_chat_image_send
            else -> R.layout.layout_temp_load_image
        }
    }

    override fun getItemViewType(position: Int): Int {
        val message = currentList[position]
        return when {
            message.idUserSend == uid && message.type == "text" -> 1
            message.idUserSend != uid && message.type == "text" -> 0
            message.idUserSend == uid && message.type == "file" -> 3
            message.idUserSend != uid && message.type == "file" -> 2
            message.idUserSend == uid && message.type == "image" -> 5
            message.idUserSend == uid && message.type == "image-temp" -> 6
            else -> 4
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
                    if (pathAvatar.isNotEmpty()) imageAvatarUserReceiver.loadWithGlide(pathAvatar)
                    //        isNeedInvisible(position, imageAvatarUserReceiver)
                }
                1 -> {
                    textSend.text = item.content
                }
                2 -> {
                    textNameFile.text =
                        if (item.namePreview.isNotEmpty()) item.namePreview else item.content
                    if (pathAvatar.isNotEmpty()) imageAvatarRec.loadWithGlide(pathAvatar)
                    //         isNeedInvisible(position, imageAvatarRec)
                    textNameFile.setOnClickListener {
                        listener(item, EnumListener.FILE)
                    }
                }
                3 -> {
                    textNameFileSend.text =
                        if (item.namePreview.isNotEmpty()) item.namePreview else item.content
                    textNameFileSend.setOnClickListener {
                        listener(item, EnumListener.FILE)
                    }
                }
                4 -> {
                    imageRec.loadWithGlide(item.content, R.drawable.bg_image_send)
                    imageRec.setOnClickListener { listener(item, EnumListener.IMAGE) }
                    if (pathAvatar.isNotEmpty()) imageUserRecI.loadWithGlide(pathAvatar)
                    //         isNeedInvisible(position, imageUserRecI)
                }
                5 -> {
                    imageSend.loadWithGlide(item.content, R.drawable.bg_image_send)
                    imageSend.setOnClickListener { listener(item, EnumListener.IMAGE) }
                }
            }
        }
    }

    fun isNeedInvisible(position: Int, imageView: ImageView) {
        if (position > 0 && position < currentList.size - 1) {
            if (currentList[position].idUserSend != currentList[position + 1].idUserSend) {
                imageView.visibility = View.INVISIBLE
            }
        }
    }
}
