package com.karl.last_chat.utils.extensions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.karl.last_chat.R

fun FragmentManager.addFragment(fragment: Fragment, container: Int=  R.id.mainContainer) {
    beginTransaction().add(container, fragment, fragment.javaClass.simpleName)
        .addToBackStack(fragment.javaClass.simpleName)
        .setCustomAnimations(R.anim.left_to_right, R.anim.right_to_left)
        .commit()
}

fun FragmentManager.replaceFragment(fragment: Fragment, container: Int) {
    beginTransaction().replace(container, fragment, fragment.javaClass.simpleName)
        .setCustomAnimations(R.anim.left_to_right, R.anim.right_to_left)
        .commit()
}

fun FragmentManager.replaceWithBackStack(fragment: Fragment, container: Int) {
    beginTransaction().replace(container, fragment, fragment.javaClass.simpleName)
        .addToBackStack(fragment.javaClass.simpleName)
        .setCustomAnimations(R.anim.left_to_right, R.anim.right_to_left)
        .commit()
}

fun FragmentManager.addFragmentWithBackStack(fragment: Fragment, container: Int) {
    beginTransaction().add(container, fragment, fragment.javaClass.simpleName)
        .addToBackStack(fragment.javaClass.simpleName)
        .setCustomAnimations(R.anim.left_to_right, R.anim.right_to_left)
        .commit()
}

fun FragmentManager.addChildFragmentWithBackStack(fragment: Fragment, container: Int) {
    beginTransaction().add(container, fragment, fragment.javaClass.simpleName)
        .addToBackStack(fragment.javaClass.simpleName)
        .setCustomAnimations(R.anim.left_to_right, R.anim.right_to_left)
        .commit()
}
