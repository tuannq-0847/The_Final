package com.karl.last_chat.view.home.group_chat

import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseFragment
import com.karl.last_chat.data.model.User
import com.karl.last_chat.utils.extensions.visibilityStateViews
import com.karl.last_chat.view.home.HomeFragment
import kotlinx.android.synthetic.main.fragment_friend_request.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class GroupFragment : BaseFragment<GroupViewModel>() {
    override val viewModel: GroupViewModel by viewModel()

    override val layoutRes: Int
        get() = R.layout.fragment_friend_request

    private val adapter by lazy { GroupAdapter(listener) }
    private val friendAdapter by lazy { FriendAdapter(callBackFriend) }

    override fun onInitComponents(view: View) {
        recyclerFriendRequest.adapter = adapter
        recyclerFriends.adapter = friendAdapter
        viewModel.getFriendRequest()
        viewModel.getFriends()
    }

    override fun onObserve() {
        viewModel.friends.observe(this, Observer {
            if (it.isEmpty()) textEmptyRequest.visibilityStateViews(textEmptyRequest)
            else textEmptyRequest.visibilityStateViews(
                textEmptyRequest,
                visibilityState = View.GONE
            )
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        })
        viewModel.usersEvent.observe(this, Observer {
            Log.d("userSize", it.size.toString())
            if (it.isEmpty()) imageEmpty.visibility = View.VISIBLE
            else {
                imageEmpty.visibility = View.INVISIBLE
                friendAdapter.submitList(it)
                friendAdapter.notifyDataSetChanged()
            }
        })
    }

    val listener = { item: User, isAccepted: Boolean ->
        if (isAccepted) {
            viewModel.acceptFriend(item.uid, UUID.randomUUID().toString())
            if (parentFragment is HomeFragment) (parentFragment as HomeFragment).bottomNavigation.selectedItemId =
                R.id.tabMessage
        } else {
            viewModel.rejectFriend(item.uid)
        }
    }

    val callBackFriend = { item: User ->

    }

    companion object {

        fun newInstance() = newInstance<GroupFragment>()
    }
}
