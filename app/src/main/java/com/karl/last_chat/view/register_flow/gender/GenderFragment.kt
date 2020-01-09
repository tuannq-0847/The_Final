package com.karl.last_chat.view.register_flow.gender

import android.content.Context
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseFragment
import com.karl.last_chat.data.model.User
import com.karl.last_chat.utils.Constants
import com.karl.last_chat.utils.extensions.onClickViews
import com.karl.last_chat.utils.extensions.replaceFragment
import com.karl.last_chat.view.home.HomeFragment
import com.karl.last_chat.view.register_flow.parent_res.ParentResFragment
import kotlinx.android.synthetic.main.fragment_first_name.buttonContinue
import kotlinx.android.synthetic.main.fragment_gender.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class GenderFragment : BaseFragment<GenderViewModel>(), View.OnClickListener {

    val user by lazy { arguments?.getParcelable<User>(Constants.USER) }
    private var gender: String = ""

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonMan -> {
                buttonMan.setTextColor(
                    resources.getColor(
                        R.color.color_selected,
                        resources.newTheme()
                    )
                )
                buttonWoman.setTextColor(
                    resources.getColor(
                        R.color.color_black,
                        resources.newTheme()
                    )
                )
                buttonMan.setBackgroundResource(R.drawable.bg_gender_enable)
                buttonWoman.setBackgroundResource(R.drawable.bg_gender)
                gender = "Male"
            }
            R.id.buttonWoman -> {
                buttonMan.setTextColor(
                    resources.getColor(
                        R.color.color_black,
                        resources.newTheme()
                    )
                )
                buttonWoman.setTextColor(
                    resources.getColor(
                        R.color.color_selected,
                        resources.newTheme()
                    )
                )
                buttonWoman.setBackgroundResource(R.drawable.bg_gender_enable)
                buttonMan.setBackgroundResource(R.drawable.bg_gender)
                gender = "Female"
            }
        }
    }

    override val viewModel: GenderViewModel by viewModel()
    override val layoutRes: Int
        get() = R.layout.fragment_gender

    override fun onInitComponents(view: View) {
        (parentFragment as ParentResFragment).animationProgress(80, 100)
        onClickViews(buttonMan, buttonWoman)
        buttonContinue.setOnClickListener {
            viewModel.insertUser(
                User(
                    userName = user!!.userName,
                    gender = gender,
                    uid = viewModel.getCurrentUId(),
                    birthday = user!!.birthday
                )
            )
        }
        hideKeyboard()
    }

    override fun onObserve() {
        viewModel.completeEvent.observe(this, Observer {
            activity?.supportFragmentManager?.replaceFragment(
                HomeFragment.newInstance(),
                R.id.mainContainer
            )
            writeSharePreferences(true)
        })
    }


    private fun writeSharePreferences(isFinish: Boolean) {
        val sharePreferences =
            activity?.getSharedPreferences(Constants.KEY_IS_FINISHED, Context.MODE_PRIVATE)
        val editor = sharePreferences?.edit()
        editor?.putBoolean(Constants.KEY_IS_FINISHED, true)
        editor?.apply()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        (parentFragment as ParentResFragment).animationProgress(100, 80)
        fragmentManager?.popBackStack()
    }

    override fun isNeedAutoBackPressed() = false

    companion object {

        fun newInstance(user: User) = newInstance<GenderFragment>(Pair(Constants.USER, user))
    }
}