package com.karl.last_chat.view.home.chat

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseRecyclerView
import com.karl.last_chat.utils.Constants
import kotlinx.android.synthetic.main.item_emoji.view.*

class EmojiAdapter(callBack: DiffUtil.ItemCallback<String>) : BaseRecyclerView<String>(callBack) {
    override fun onBind(itemView: View, item: String) {
        Log.d("itemView", item)
        itemView.textEmoji.text = Constants.BASE_EMOJI + item
    }

    override fun getLayoutRes(viewType: Int): Int = R.layout.item_emoji

}
