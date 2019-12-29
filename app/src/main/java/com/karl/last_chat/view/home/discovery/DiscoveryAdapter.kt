package com.karl.last_chat.view.home.discovery

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.karl.last_chat.data.model.User
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseRecyclerView
import com.karl.last_chat.utils.extensions.loadWithGlide
import kotlinx.android.synthetic.main.item_discovery.view.*

class DiscoveryAdapter(onClickItem: (item: User) -> Unit) :
    BaseRecyclerView<User>(object : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.uid == newItem.uid
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

    }, onClickItem) {
    override fun getLayoutRes(viewType: Int) = R.layout.item_discovery

    override fun onBind(itemView: View, item: User) {
        itemView.imageUser.loadWithGlide(item.pathAvatar)
        itemView.textNameUser.text = item.userName
    }
}