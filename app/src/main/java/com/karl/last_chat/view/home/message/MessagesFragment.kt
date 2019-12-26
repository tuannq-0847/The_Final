package com.karl.last_chat.view.home.message

import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseFragment
import com.karl.last_chat.data.model.LastMessage
import kotlinx.android.synthetic.main.message_layout.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MessagesFragment : BaseFragment<MessagesViewModel>() {
    override val viewModel: MessagesViewModel by viewModel()
    override val layoutRes: Int = R.layout.message_layout

    private val messageAdapter by lazy {
        MessageAdapter {

        }
    }

    override fun onInitComponents(view: View) {
        viewModel.getMessagesList()
    }

    override fun onObserve() {
        viewModel.messageEvents.observe(this, Observer {
            viewModel.hideLoading()
            if (it.isEmpty()) {
                layoutFind.visibility = View.VISIBLE
                textGetStarted.visibility = View.VISIBLE
            } else {
                recyclerMessages.adapter = messageAdapter
                messageAdapter.submitList(it)
            }
        })
    }

    companion object {

        fun newInstance() = newInstance<MessagesFragment>()
    }
}
