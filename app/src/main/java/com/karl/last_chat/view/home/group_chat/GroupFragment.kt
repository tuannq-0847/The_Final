package com.karl.last_chat.view.home.group_chat

import android.view.View
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class GroupFragment : BaseFragment<GroupViewModel>() {
    override val viewModel: GroupViewModel by viewModel()

//    val adapter = EmojiAdapter(object : DiffUtil.ItemCallback<String>() {
//        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
//            return true
//        }
//
//        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
//            return true
//        }
//
//    })

    override val layoutRes: Int
        get() = R.layout.fragment_chat

    override fun onInitComponents(view: View) {
        //  recyclerChat.adapter = adapter
        //    adapter.submitList(viewModel.listEmojis)
    }

    override fun onObserve() {

    }

    companion object {
        
        fun newInstance() = newInstance<GroupFragment>()
    }
}