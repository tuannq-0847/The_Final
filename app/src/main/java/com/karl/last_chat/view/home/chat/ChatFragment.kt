package com.karl.last_chat.view.home.chat

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseFragment
import com.karl.last_chat.data.model.Message
import com.karl.last_chat.data.model.Notification
import com.karl.last_chat.utils.extensions.onClickViews
import com.karl.last_chat.utils.extensions.visibilityStateViews
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.android.synthetic.main.layout_toolbar.*
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
                if (isClicked) hideKeyBoard() else showKeyBoard()
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
            R.id.imageBack -> {
                onBackPressed()
            }
        }
    }

    override val viewModel: ChatViewModel by viewModel()

    private val adapter = EmojiAdapter {
        editChat.setText(editChat.text.toString() + it)
    }

    private fun hideKeyBoard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun showKeyBoard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInputFromWindow(view?.windowToken, InputMethodManager.SHOW_FORCED, 0)
    }

    private val chatAdapter by lazy { ChatAdapter(viewModel.getCurrentUser()!!.uid) }

    override val layoutRes: Int
        get() = R.layout.fragment_chat

    override fun onInitComponents(view: View) {
        onClickViews(imageEmoji, editChat, imageSend, imageBack)
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
            if (it.size > 0) recyclerChat.smoothScrollToPosition(it.size - 1)
        })
        viewModel.isSend.observe(this, Observer {
            viewModel.saveNotification(uid!!, Notification(it.content, it.idUserSend))
        })
        viewModel.userEvent.observe(this, Observer {
            chatAdapter.setAvatar(it.pathAvatar)
            textSpannable.text = it.userName
        })
    }

    companion object {

        fun newInstance(userId: String) = newInstance<ChatFragment>(Pair(USER_ID, userId))
        const val USER_ID = "USER_ID"
    }
}
