package com.karl.last_chat.view.home.discovery

import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_discovery.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DiscoveryFragment : BaseFragment<DiscoveryViewModel>() {
    override val viewModel: DiscoveryViewModel by viewModel()
    override val layoutRes: Int = R.layout.fragment_discovery

    private val discoveryAdapter = DiscoveryAdapter {

    }

    override fun onInitComponents(view: View) {
        recyclerDiscovery.adapter = discoveryAdapter
        viewModel.getUsers()
        viewModel.showLoading()
    }

    override fun onObserve() {
        viewModel.userAroundHere.observe(this, Observer {
            Log.d("mutable", it.size.toString())
            discoveryAdapter.submitList(it)
            viewModel.hideLoading()
        })
    }

    companion object {

        fun newInstance() = newInstance<DiscoveryFragment>()
    }
}
