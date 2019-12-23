package com.karl.last_chat.view.home.message

import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseFragment
import kotlinx.android.synthetic.main.message_layout.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MessagesFragment : BaseFragment<MessagesViewModel>() {
    override val viewModel: MessagesViewModel by viewModel()
    override val layoutRes: Int = R.layout.message_layout

    override fun onInitComponents(view: View) {
        viewModel.getMessagesList()
    }

    override fun onObserve() {
        viewModel.messageEvents.observe(this, Observer {
            Log.d("message", it.size.toString())
            if (it.isEmpty()) {
                layoutFind.visibility = View.VISIBLE
                textGetStarted.visibility = View.VISIBLE
            }
        })
    }

    companion object {

        fun newInstance() = newInstance<MessagesFragment>()
    }
}
