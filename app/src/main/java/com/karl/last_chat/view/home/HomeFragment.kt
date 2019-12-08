package com.karl.last_chat.view.home

import android.view.View
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<HomeViewModel>() {
    override val viewModel: HomeViewModel by viewModel()
    override val layoutRes: Int
        get() = R.layout.fragment_home

    override fun onInitComponents(view: View) {

    }

    override fun onObserve() {

    }

    companion object{

        fun newInstance() = newInstance<HomeFragment>()
    }
}
