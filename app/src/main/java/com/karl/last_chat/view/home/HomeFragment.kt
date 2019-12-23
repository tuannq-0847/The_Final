package com.karl.last_chat.view.home

import android.view.MenuItem
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseFragment
import com.karl.last_chat.utils.extensions.replaceFragment
import com.karl.last_chat.view.home.chat.ChatFragment
import com.karl.last_chat.view.home.message.MessagesFragment
import com.karl.last_chat.view.personal.PersonalFragment
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<HomeViewModel>(),
    BottomNavigationView.OnNavigationItemSelectedListener {

    override val viewModel: HomeViewModel by viewModel()
    override val layoutRes: Int
        get() = R.layout.fragment_home

    override fun onInitComponents(view: View) {
        bottomNavigation.setOnNavigationItemSelectedListener(this)
        val isFirst = arguments!!.getBoolean(IS_FIRST)
        if (isFirst) {
            bottomNavigation.selectedItemId = R.id.tabPersonal
            fragmentManager?.replaceFragment(PersonalFragment.newInstance(), R.id.homeContainer)
        } else {
            fragmentManager?.replaceFragment(
                MessagesFragment.newInstance(),
                R.id.homeContainer
            )
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.tabSearch -> {

            }
            R.id.tabMessage -> {
                fragmentManager?.replaceFragment(
                    MessagesFragment.newInstance(),
                    R.id.homeContainer
                )
            }
            R.id.tabGroup -> {

            }
            R.id.tabPersonal -> {
                fragmentManager?.replaceFragment(
                    PersonalFragment.newInstance(),
                    R.id.homeContainer
                )
            }
        }
        return true
    }

    override fun onObserve() {

    }

    companion object {

        fun newInstance(isFirstOpen: Boolean = false) =
            newInstance<HomeFragment>(Pair(IS_FIRST, isFirstOpen))

        private const val IS_FIRST = "IS_FIRST"
    }
}
