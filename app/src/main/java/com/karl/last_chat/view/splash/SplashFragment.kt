package com.karl.last_chat.view.splash

import android.os.Handler
import android.view.View
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseFragment
import com.karl.last_chat.utils.extensions.replaceFragment
import com.karl.last_chat.view.auth.AuthFragment
import com.karl.last_chat.view.home.HomeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : BaseFragment<SplashViewModel>() {
    override val viewModel: SplashViewModel by viewModel()

    override val layoutRes: Int = R.layout.fragment_splash

    override fun onInitComponents(view: View) {
        Handler().postDelayed({
            if (viewModel.isLoggedIn()) {
                fragmentManager?.replaceFragment(HomeFragment.newInstance(), R.id.mainContainer)
            } else {
                fragmentManager?.replaceFragment(AuthFragment.newInstance(), R.id.mainContainer)
            }
        }, 1000)
    }

    override fun onObserve() {

    }

    companion object {

        fun newInstance() = newInstance<SplashFragment>()
    }
}