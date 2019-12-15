package com.karl.last_chat

import com.karl.last_chat.base.BaseActivity
import com.karl.last_chat.utils.extensions.addFragment
import com.karl.last_chat.utils.extensions.replaceFragment
import com.karl.last_chat.view.auth.AuthFragment
import com.karl.last_chat.view.splash.SplashFragment

class MainActivity : BaseActivity() {
    override val layoutRes: Int
        get() = R.layout.activity_main

    override fun onInitComponents() {
       // supportFragmentManager.addFragment(AuthFragment.newInstance(), R.id.mainContainer)
        supportFragmentManager.replaceFragment(SplashFragment.newInstance(), R.id.mainContainer)
    }
}
