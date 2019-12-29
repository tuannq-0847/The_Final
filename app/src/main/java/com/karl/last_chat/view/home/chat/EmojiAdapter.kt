package com.karl.last_chat.view.home.chat

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseRecyclerView
import kotlinx.android.synthetic.main.item_emoji.view.*

class EmojiAdapter(val listener: (item: String) -> Unit) :
    BaseRecyclerView<String>(object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }) {
    override fun onBind(itemView: View, item: String) {
        itemView.textEmoji.text = item
        itemView.setOnClickListener { listener(item) }
    }

    override fun getLayoutRes(viewType: Int): Int = R.layout.item_emoji

}
