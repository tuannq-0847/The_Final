package com.karl.last_chat.view.auth

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter

class AuthPagerAdapter(private val fragments: List<Fragment>, fm: FragmentManager, behavior: Int) :
    FragmentStatePagerAdapter(fm, behavior) {
    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getPageWidth(position: Int): Float {
        return 0.87f
    }

    override fun getCount(): Int {
        return fragments.size
    }
}
