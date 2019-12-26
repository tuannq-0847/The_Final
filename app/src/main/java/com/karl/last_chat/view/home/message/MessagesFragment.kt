package com.karl.last_chat.view.home.message

import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseFragment
import com.karl.last_chat.utils.extensions.onClickViews
import com.karl.last_chat.utils.extensions.replaceFragment
import com.karl.last_chat.view.home.HomeFragment
import com.karl.last_chat.view.home.discovery.DiscoveryFragment
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.message_layout.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MessagesFragment : BaseFragment<MessagesViewModel>(), View.OnClickListener {

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.textTap -> {
                if (parentFragment is HomeFragment) {
                    (parentFragment as HomeFragment).bottomNavigation.selectedItemId =
                        R.id.tabSearch
                }
            }
        }
    }

    override val viewModel: MessagesViewModel by viewModel()
    override val layoutRes: Int = R.layout.message_layout

    private val messageAdapter by lazy {
        MessageAdapter {

        }
    }

    override fun onInitComponents(view: View) {
        onClickViews(textTap)
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

        private const val HOME = "HOME"
    }
}
