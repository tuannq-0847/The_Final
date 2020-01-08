package com.karl.last_chat.view.home.group_chat

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseRecyclerView
import com.karl.last_chat.data.model.User
import com.karl.last_chat.utils.extensions.loadWithGlide
import kotlinx.android.synthetic.main.item_friend.view.*

class FriendAdapter(onClickItem: (item: User) -> Unit) :
    BaseRecyclerView<User>(object : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.uid == newItem.uid
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.uid == newItem.uid
        }

    }, onClickItem) {
    override fun getLayoutRes(viewType: Int) = R.layout.item_friend

    override fun onBind(itemView: View, item: User, position: Int) {
        itemView.run {
            imageFriends.loadWithGlide(item.pathAvatar)
            textNameUser.text = item.userName
        }
    }
}
