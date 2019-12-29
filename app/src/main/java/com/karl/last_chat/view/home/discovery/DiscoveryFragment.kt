package com.karl.last_chat.view.home.discovery

import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DiffUtil
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseFragment
import com.karl.last_chat.utils.extensions.addFragment
import com.karl.last_chat.view.profile.ProfileFragment
import com.yuyakaido.android.cardstackview.*
import kotlinx.android.synthetic.main.fragment_discovery.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DiscoveryFragment : BaseFragment<DiscoveryViewModel>(), CardStackListener {


    override fun onCardDisappeared(view: View?, position: Int) {
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {
    }

    override fun onCardSwiped(direction: Direction?) {
    }

    override fun onCardCanceled() {
    }

    override fun onCardAppeared(view: View?, position: Int) {
    }

    override fun onCardRewound() {
    }

    override val viewModel: DiscoveryViewModel by viewModel()
    override val layoutRes: Int = R.layout.fragment_discovery
    private val manager by lazy { CardStackLayoutManager(context, this) }

    private val discoveryAdapter = DiscoveryAdapter {
        activity?.supportFragmentManager?.addFragment(
            ProfileFragment.newInstance(it.uid),
            R.id.mainContainer
        )
    }

    override fun onInitComponents(view: View) {
        manager.setStackFrom(StackFrom.None)
        //   manager.setVisibleCount(3)
        manager.setTranslationInterval(8.0f)
        manager.setScaleInterval(0.95f)
        manager.setSwipeThreshold(0.3f)
        manager.setMaxDegree(20.0f)
        manager.setDirections(Direction.HORIZONTAL)
        manager.setCanScrollHorizontal(true)
        manager.setCanScrollVertical(true)
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
        manager.setOverlayInterpolator(LinearInterpolator())
        recyclerDiscovery.layoutManager = manager
        recyclerDiscovery.adapter = discoveryAdapter
        recyclerDiscovery.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }
        viewModel.getUsers()
        viewModel.showLoading()
    }

    override fun onObserve() {
        viewModel.userAroundHere.observe(this, Observer {
            discoveryAdapter.submitList(it)
            viewModel.hideLoading()
        })
    }

    companion object {

        fun newInstance() = newInstance<DiscoveryFragment>()
    }
}
