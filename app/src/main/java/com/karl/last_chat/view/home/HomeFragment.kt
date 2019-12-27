package com.karl.last_chat.view.home

import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseFragment
import com.karl.last_chat.utils.extensions.replaceFragment
import com.karl.last_chat.view.home.chat.ChatFragment
import com.karl.last_chat.view.home.discovery.DiscoveryFragment
import com.karl.last_chat.view.home.group_chat.GroupFragment
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
            childFragmentManager.replaceFragment(
                ChatFragment.newInstance(),
                R.id.homeContainer
            )
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.tabSearch -> {
                fragmentManager?.replaceFragment(
                    DiscoveryFragment.newInstance(),
                    R.id.homeContainer
                )
            }
            R.id.tabMessage -> {
                fragmentManager?.replaceFragment(
                    ChatFragment.newInstance(),
                    R.id.homeContainer
                )
            }
            R.id.tabGroup -> {
                fragmentManager?.replaceFragment(GroupFragment.newInstance(), R.id.homeContainer)
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
