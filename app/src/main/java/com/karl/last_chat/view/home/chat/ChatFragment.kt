package com.karl.last_chat.view.home.chat

import android.view.View
import androidx.core.widget.addTextChangedListener
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseFragment
import com.karl.last_chat.utils.extensions.onClickViews
import kotlinx.android.synthetic.main.fragment_chat.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChatFragment : BaseFragment<ChatViewModel>(), View.OnClickListener {

    private var isClicked = true
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imageEmoji -> {
                recyclerEmoji.visibility = if (isClicked) View.VISIBLE else View.GONE
                isClicked = !isClicked
            }
            R.id.editChat -> {
                recyclerEmoji.visibility = View.GONE
            }
        }
    }

    override val viewModel: ChatViewModel by viewModel()

    private val adapter = EmojiAdapter {
        editChat.setText(editChat.text.toString() + it)
    }

    override val layoutRes: Int
        get() = R.layout.fragment_chat

    override fun onInitComponents(view: View) {
        onClickViews(imageEmoji, editChat)
        recyclerEmoji.adapter = adapter
        adapter.submitList(viewModel.listEmojis)
        editChat.addTextChangedListener {
            if (it!!.isNotEmpty()) {
                imageAttach.visibility = View.INVISIBLE
                imagePic.visibility = View.INVISIBLE
                imageSend.visibility = View.VISIBLE
            } else {
                imageAttach.visibility = View.VISIBLE
                imagePic.visibility = View.VISIBLE
                imageSend.visibility = View.INVISIBLE
            }
        }
    }

    override fun onObserve() {

    }

    companion object {

        fun newInstance() = newInstance<ChatFragment>()
    }
}
