package com.karl.last_chat.view.home.discovery

import android.view.View
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class DiscoveryFragment : BaseFragment<DiscoveryViewModel>() {
    override val viewModel: DiscoveryViewModel by viewModel()
    override val layoutRes: Int = R.layout.fragment_discovery

    override fun onInitComponents(view: View) {

    }

    override fun onObserve() {

    }
}