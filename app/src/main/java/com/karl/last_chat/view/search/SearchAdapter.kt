package com.karl.last_chat.view.search

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseRecyclerView
import com.karl.last_chat.data.model.User
import com.karl.last_chat.utils.extensions.loadWithGlide
import kotlinx.android.synthetic.main.item_search_chat.view.*

class SearchAdapter(onClickItem: (item: User) -> Unit) :
    BaseRecyclerView<User>(object : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.uid == newItem.uid
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

    }, onClickItem) {
    override fun getLayoutRes(viewType: Int): Int = R.layout.item_search_chat

    override fun onBind(itemView: View, item: User, position: Int) {
        itemView.run {
            textNameSearch.text = item.userName
            imageAvatarSearch.loadWithGlide(item.pathAvatar)
        }
    }
}