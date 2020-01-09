package com.karl.last_chat.view.search

import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseFragment
import com.karl.last_chat.data.model.User
import com.karl.last_chat.utils.extensions.addFragment
import com.karl.last_chat.utils.extensions.onClickViews
import com.karl.last_chat.view.profile.ProfileFragment
import kotlinx.android.synthetic.main.layout_search_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment<SearchViewModel>(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imageBackSearch -> {
                activity?.onBackPressed()
            }
        }
    }

    override val viewModel: SearchViewModel by viewModel()
    override val layoutRes: Int
        get() = R.layout.layout_search_detail

    private val searchAdapter by lazy { SearchAdapter(listener) }

    override fun onInitComponents(view: View) {
        recyclerSearch.adapter = searchAdapter
        onClickViews(imageBackSearch)
        editSearchFriend.addTextChangedListener {
            viewModel.queryUserName(it.toString())
        }
    }

    private val listener: (data: User) -> Unit = {
        fragmentManager?.addFragment(ProfileFragment.newInstance(it.uid))
    }

    override fun onObserve() {
        viewModel.eventUserQuery.observe(this, Observer {
            searchAdapter.submitList(it)
            searchAdapter.notifyDataSetChanged()
        })
    }

    companion object {

        fun newInstance() = newInstance<SearchFragment>()
    }
}