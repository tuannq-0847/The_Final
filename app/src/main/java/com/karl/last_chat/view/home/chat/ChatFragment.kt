package com.karl.last_chat.view.home.chat

import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseFragment
import com.karl.last_chat.data.model.Message
import com.karl.last_chat.data.model.Notification
import com.karl.last_chat.utils.extensions.onClickViews
import com.karl.last_chat.utils.extensions.visibilityStateViews
import kotlinx.android.synthetic.main.fragment_chat.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class ChatFragment : BaseFragment<ChatViewModel>(), View.OnClickListener {

    private val uid by lazy { arguments?.getString(USER_ID) }
    private var dId = ""

    // private val tempList by lazy { mutableListOf<Message>() }

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
            R.id.imageSend -> {
                viewModel.sendMessage(
                    dId, Message(
                        content = editChat.text.toString(),
                        idUserSend = viewModel.getCurrentUser()!!.uid,
                        idUserRec = uid!!
                    )
                )
                editChat.setText("")
            }
        }
    }

    override val viewModel: ChatViewModel by viewModel()

    private val adapter = EmojiAdapter {
        editChat.setText(editChat.text.toString() + it)
    }

    private val chatAdapter by lazy { ChatAdapter(viewModel.getCurrentUser()!!.uid) }

    override val layoutRes: Int
        get() = R.layout.fragment_chat

    override fun onInitComponents(view: View) {
        onClickViews(imageEmoji, editChat, imageSend)
        recyclerEmoji.adapter = adapter
        recyclerChat.adapter = chatAdapter
        adapter.submitList(viewModel.listEmojis)
        editChat.addTextChangedListener {
            if (it!!.isNotEmpty()) {
                view.visibilityStateViews(imageSend)
                view.visibilityStateViews(imageAttach, imagePic, visibilityState = View.INVISIBLE)
            } else {
                view.visibilityStateViews(imageSend, visibilityState = View.INVISIBLE)
                view.visibilityStateViews(imageAttach, imagePic)
            }
        }
        viewModel.getDisscussId(uid!!)
        viewModel.getInforUser(uid!!)
    }

    override fun onObserve() {
        viewModel.idDiscuss.observe(this, Observer {
            if (it != "") {
                dId = it
                viewModel.getMesages(dId)
            } else {
                viewModel.setDisscussId(uid!!, UUID.randomUUID().toString())
            }
        })
        viewModel.messages.observe(this, Observer {
            chatAdapter.submitList(it)
            chatAdapter.notifyDataSetChanged()
            recyclerChat.smoothScrollToPosition(it.size - 1)
        })
        viewModel.isSend.observe(this, Observer {
            viewModel.saveNotification(uid!!, Notification(it.content, it.idUserSend))
        })
    }

    companion object {

        fun newInstance(userId: String) = newInstance<ChatFragment>(Pair(USER_ID, userId))
        const val USER_ID = "USER_ID"
    }
}
