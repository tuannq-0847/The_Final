package com.karl.last_chat.view.home.group_chat

import android.view.View
import androidx.lifecycle.Observer
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseFragment
import com.karl.last_chat.data.model.User
import com.karl.last_chat.utils.extensions.visibilityStateViews
import kotlinx.android.synthetic.main.fragment_friend_request.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class GroupFragment : BaseFragment<GroupViewModel>() {
    override val viewModel: GroupViewModel by viewModel()

    override val layoutRes: Int
        get() = R.layout.fragment_friend_request

    private val adapter by lazy { GroupAdapter(listener) }

    override fun onInitComponents(view: View) {
        recyclerFriendRequest.adapter = adapter
        viewModel.getFriendRequest()
    }

    override fun onObserve() {
        viewModel.friends.observe(this, Observer {
            if (it.isEmpty()) textEmptyRequest.visibilityStateViews(textEmptyRequest)
            else textEmptyRequest.visibilityStateViews(
                textEmptyRequest,
                visibilityState = View.GONE
            )
            adapter.submitList(it)
        })
    }

    val listener = { item: User, isAccepted: Boolean ->

    }

    companion

    object {

        fun newInstance() = newInstance<GroupFragment>()
    }
}
