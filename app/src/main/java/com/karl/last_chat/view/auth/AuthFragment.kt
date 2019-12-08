package com.karl.last_chat.view.auth

import android.animation.ObjectAnimator
import android.graphics.Path
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseFragment
import com.karl.last_chat.base.BaseViewModel
import com.karl.last_chat.view.login.LoginFragment
import com.karl.last_chat.view.register.RegisterFragment
import kotlinx.android.synthetic.main.fragment_auth.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_register.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthFragment : BaseFragment<AuthViewModel>(), ViewPager.OnPageChangeListener {
    override fun onObserve() {

    }

    override val viewModel: AuthViewModel by viewModel()

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        (fragments[0] as LoginFragment).textVerticalLogin.visibility =
            if (fragments[position] is LoginFragment) View.GONE else View.VISIBLE
        //     handleAnimation(fragments[0].textVerticalLogin)
//        rotateAnim(fragments[0].textVerticalLogin)

        (fragments[1] as RegisterFragment).textVerticalSignUp.visibility =
            if (fragments[position] is RegisterFragment) View.GONE else View.VISIBLE
    }

//    fun handleAnimation(view: View) {
//        val path = Path().apply {
//            arcTo(0f, 0f, 1000f, 1000f, 270f, -180f, true)
//        }
//        ObjectAnimator.ofFloat(view, View.X, View.Y, path).apply {
//            duration = 500
//            start()
//        }
//    }
//
//    fun rotateAnim(view: View) {
//        val rotateAnimation = RotateAnimation(
//            180F, 360F, Animation.RELATIVE_TO_SELF,
//            0.5f, Animation.RELATIVE_TO_SELF, 0.5f
//        )
//        rotateAnimation.duration = 500
//        view.startAnimation(rotateAnimation)
//    }

    override val layoutRes: Int
        get() = R.layout.fragment_auth

    override fun onInitComponents(view: View) {
        viewPagerAuth.adapter = pagerAdapter
        viewPagerAuth.addOnPageChangeListener(this)
    }

    private val fragments =
        mutableListOf<Fragment>(LoginFragment.newInstance(), RegisterFragment.newInstance())

    private val pagerAdapter by lazy {
        AuthPagerAdapter(
            fragments,
            activity!!.supportFragmentManager,
            1
        )
    }

    companion object {

        fun newInstance() = newInstance<AuthFragment>()
    }
}
