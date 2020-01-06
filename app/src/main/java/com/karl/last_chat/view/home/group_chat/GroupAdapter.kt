package com.karl.last_chat.view.home.group_chat

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseRecyclerView
import com.karl.last_chat.data.model.Message
import com.karl.last_chat.data.model.User
import com.karl.last_chat.utils.extensions.loadWithGlide
import kotlinx.android.synthetic.main.item_friend_request.view.*

class GroupAdapter(private val listener: (item: User, isAccepted: Boolean) -> Unit) :
    BaseRecyclerView<User>(object : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.uid == newItem.uid
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

    }) {
    override fun getLayoutRes(viewType: Int): Int = R.layout.item_friend_request

    override fun onBind(itemView: View, item: User, position: Int) {
        itemView.run {
            imageAvatarUserRequest.loadWithGlide(item.pathAvatar)
            textNameUserRequest.text = item.userName
            textGender.text = item.gender
            buttonAccept.setOnClickListener {
                listener(item, true)
            }
            buttonCancel.setOnClickListener {
                listener(item, false)
            }
        }
    }
}