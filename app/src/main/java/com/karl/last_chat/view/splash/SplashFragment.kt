package com.karl.last_chat.view.splash

import android.view.View
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.os.Handler
import com.karl.last_chat.utils.extensions.replaceFragment
import com.karl.last_chat.view.auth.AuthFragment

class SplashFragment : BaseFragment<SplashViewModel>() {
    override val viewModel: SplashViewModel by viewModel()

    override val layoutRes: Int = R.layout.fragment_splash

    override fun onInitComponents(view: View) {
        Handler().postDelayed({
            fragmentManager?.replaceFragment(AuthFragment.newInstance(), R.id.mainContainer)
        }, 2000)
    }

    override fun onObserve() {

    }

    companion object {

        fun newInstance() = newInstance<SplashFragment>()
    }
}