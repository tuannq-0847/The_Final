package com.karl.last_chat.view.home.chat

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseFragment
import com.karl.last_chat.data.model.Message
import com.karl.last_chat.data.model.Notification
import com.karl.last_chat.utils.Constants
import com.karl.last_chat.utils.extensions.onClickViews
import com.karl.last_chat.utils.extensions.showMessage
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
                if (editChat.text.toString() != "") {
                    viewModel.sendMessage(
                        dId, Message(
                            content = editChat.text.toString(),
                            idUserSend = viewModel.getCurrentUser()!!.uid,
                            idUserRec = uid!!,
                            seen = viewModel.getCurrentUser()!!.uid,
                            type = "text"
                        )
                    )
                    editChat.setText(Constants.EMPTY)
                } else context?.showMessage(getString(R.string.not_type))
            }
            R.id.imageBack -> {
                activity?.onBackPressed()
            }
            R.id.imagePic -> {
                val intent = Intent()
                intent.action = Intent.ACTION_GET_CONTENT
                intent.type = Constants.INTENT_GALLERY
                startActivityForResult(
                    Intent.createChooser(
                        intent,
                        resources.getString(R.string.choose_image)
                    ),
                    32
                )
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
        onClickViews(imageEmoji, editChat, imageSend, imageBack, imagePic)
        recyclerEmoji.adapter = adapter
        recyclerChat.adapter = chatAdapter
        adapter.submitList(viewModel.listEmojis)
        dId = arguments?.getString(DISCUSS_ID) ?: ""
        editChat.addTextChangedListener {
            if (it!!.isNotEmpty()) {
                view.visibilityStateViews(imageSend)
                view.visibilityStateViews(imageAttach, imagePic, visibilityState = View.INVISIBLE)
            } else {
                view.visibilityStateViews(imageSend, visibilityState = View.INVISIBLE)
                view.visibilityStateViews(imageAttach, imagePic)
            }
        }
        if (dId.isEmpty()) {
            viewModel.getDisscussId(viewModel.getCurrentUser()!!.uid, uid!!)
        } else handleMessage(dId)
        viewModel.getInforUser(uid!!)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 32 && resultCode == Activity.RESULT_OK) {
            viewModel.a.add(
                Message(
                    content = "abc",
                    idUserSend = viewModel.getCurrentUser()!!.uid,
                    idUserRec = uid!!,
                    seen = viewModel.getCurrentUser()!!.uid,
                    type = "image-temp"
                )
            )
            chatAdapter.submitList(viewModel.a)
            chatAdapter.notifyDataSetChanged()
            recyclerChat.scrollToPosition(viewModel.a.size - 1)
            viewModel.uploadImage(
                uid!!,
                dId,
                data?.data!!
            )
        }
    }

//    fun compressBitmap(uri: Uri): ByteArray {
//        val bitmap = BitmapFactory.decodeStream(activity?.contentResolver?.openInputStream(uri))
//        val temp = bitmap?.rotate(bitmap, getOrientation(uri))
//        val outputStream = ByteArrayOutputStream()
//        temp?.compress(Bitmap.CompressFormat.JPEG, 10, outputStream)
//        return outputStream.toByteArray()
//    }

    override fun onObserve() {
        viewModel.idDiscuss.observe(this, Observer {
            if (it != "") {
                dId = it
                handleMessage(dId)
            } else {
                viewModel.setDisscussId(
                    uid!!,
                    UUID.randomUUID().toString()
                )
            }
        })
        viewModel.messages.observe(this, Observer {
            if (it.size == 1 && it[0].type == "new") {
                groupMessage.visibility = View.VISIBLE
                recyclerChat.visibility = View.INVISIBLE
            } else {
                it.removeAt(0)
                groupMessage.visibility = View.GONE
                recyclerChat.visibility = View.VISIBLE
            }

            chatAdapter.submitList(it)
            chatAdapter.notifyDataSetChanged()
            if (it.size > 0) recyclerChat.scrollToPosition(it.size - 1)
        })
        viewModel.isSend.observe(this, Observer {
            viewModel.saveNotification(uid!!, Notification(it.content, it.idUserSend))
        })
        viewModel.userEvent.observe(this, Observer {
            chatAdapter.setAvatar(it.pathAvatar)
            textSpannable.text = it.userName
        })
        viewModel.eventUploadImage.observe(this, Observer {
            chatAdapter.notifyDataSetChanged()
        })
    }

    private fun handleMessage(dId: String) {
        viewModel.getMesages(dId)
        viewModel.getChildKey(dId, viewModel.getCurrentUser()!!.uid)
    }

    companion object {

        fun newInstance(userId: String, idDiscuss: String = "") =
            newInstance<ChatFragment>(Pair(USER_ID, userId), Pair(DISCUSS_ID, idDiscuss))

        const val USER_ID = "USER_ID"
        const val DISCUSS_ID = "DISCUSS_ID"
    }
}
