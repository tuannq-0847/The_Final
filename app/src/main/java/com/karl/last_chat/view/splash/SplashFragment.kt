package com.karl.last_chat.view.splash

import android.content.Context
import android.os.Handler
import android.view.View
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseFragment
import com.karl.last_chat.utils.Constants
import com.karl.last_chat.utils.extensions.replaceFragment
import com.karl.last_chat.view.auth.AuthFragment
import com.karl.last_chat.view.home.HomeFragment
import com.karl.last_chat.view.register_flow.parent_res.ParentResFragment
import com.karl.last_chat.view.register_flow.welcome.ResWelcomeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : BaseFragment<SplashViewModel>() {
    override val viewModel: SplashViewModel by viewModel()

    override val layoutRes: Int = R.layout.fragment_splash

    override fun onInitComponents(view: View) {
        Handler().postDelayed({
            if (viewModel.isLoggedIn()) {
                if (checkSharePreferences()) {
                    fragmentManager?.replaceFragment(HomeFragment.newInstance(), R.id.mainContainer)
                } else {
                    fragmentManager?.replaceFragment(
                        ParentResFragment.newInstance(),
                        R.id.mainContainer
                    )
                }
            } else {
                fragmentManager?.replaceFragment(AuthFragment.newInstance(), R.id.mainContainer)
            }
        }, 1000)
    }

    override fun onObserve() {

    }


    private fun checkSharePreferences(): Boolean {
        val sharePreferences =
            activity?.getSharedPreferences(Constants.KEY_IS_FINISHED, Context.MODE_PRIVATE)
        return sharePreferences!!.getBoolean(Constants.KEY_IS_FINISHED, true)
    }


    companion object {

        fun newInstance() = newInstance<SplashFragment>()
    }
}