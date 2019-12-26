package com.karl.last_chat.view.home.chat

import android.view.View
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseFragment
import com.karl.last_chat.utils.extensions.onClickViews
import kotlinx.android.synthetic.main.fragment_chat.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChatFragment : BaseFragment<ChatViewModel>(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imageEmoji -> {
                recyclerEmoji.visibility = View.VISIBLE
            }
            R.id.editChat -> {
                recyclerEmoji.visibility = View.GONE
            }
        }
    }

    override val viewModel: ChatViewModel by viewModel()

    private val adapter = EmojiAdapter()

    override val layoutRes: Int
        get() = R.layout.fragment_chat

    override fun onInitComponents(view: View) {
        onClickViews(imageEmoji, editChat)
        recyclerEmoji.adapter = adapter
        adapter.submitList(viewModel.listEmojis)
    }

    override fun onObserve() {

    }

    companion object {

        fun newInstance() = newInstance<ChatFragment>()
    }
}
