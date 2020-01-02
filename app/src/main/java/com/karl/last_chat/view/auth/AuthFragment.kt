package com.karl.last_chat.view.auth

import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseFragment
import com.karl.last_chat.view.login.LoginFragment
import com.karl.last_chat.view.register.RegisterFragment
import com.karl.last_chat.widget.TheLastViewPager
import kotlinx.android.synthetic.main.fragment_auth.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_register.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class AuthFragment : BaseFragment<AuthViewModel>(), ViewPager.OnPageChangeListener,
    TheLastViewPager.OnSwipeOutListener {

    private var thresholdOffsetPixels = 1
    private var thresholdOffset = 0.5f
    private var scrollStarted: Boolean = false
    private var checkDirection: Boolean = false
    override fun onSwipeOutAtEnd() {
        Log.d("AuthPager", "onSwipeOutAtEnd....")
    }

    override fun onSwipeOutAtStart() {
        Log.d("AuthPager", "onSwipeOutAtStart....")
    }

    override fun onObserve() {

    }

    override val viewModel: AuthViewModel by viewModel()

    override fun onPageScrollStateChanged(state: Int) {
        if (!scrollStarted && state == ViewPager.SCROLL_STATE_DRAGGING) {
            scrollStarted = true
            checkDirection = true
        } else {
            scrollStarted = false
        }
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        if(checkDirection){
            if (thresholdOffset > positionOffset && positionOffsetPixels > thresholdOffsetPixels) {
                Log.d("onPageScrolled", "going left")
            } else {
                Log.d("onPageScrolled", "going right")
            }
            checkDirection = false
        }
    }

    override fun onPageSelected(position: Int) {
        Log.d("onPageSelected", position.toString())
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
        viewPagerAuth.setOnSwipeOutListener(this)
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
