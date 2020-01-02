package com.karl.last_chat.view.register_flow.gender

import android.content.Context
import android.view.View
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseFragment
import com.karl.last_chat.utils.Constants
import com.karl.last_chat.utils.extensions.replaceFragment
import com.karl.last_chat.view.home.HomeFragment
import com.karl.last_chat.view.register_flow.parent_res.ParentResFragment
import kotlinx.android.synthetic.main.fragment_first_name.*
import kotlinx.android.synthetic.main.layout_parent_register.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class GenderFragment : BaseFragment<GenderViewModel>() {
    override val viewModel: GenderViewModel by viewModel()
    override val layoutRes: Int
        get() = R.layout.fragment_gender

    override fun onInitComponents(view: View) {
        (parentFragment as ParentResFragment).frameProgress.progress = 100
    }

    override fun onObserve() {
        buttonContinue.setOnClickListener {
            activity?.supportFragmentManager?.replaceFragment(
                HomeFragment.newInstance(),
                R.id.mainContainer
            )
            writeSharePreferences(true)
        }
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
        (parentFragment as ParentResFragment).frameProgress.progress = 80
        fragmentManager?.popBackStack()
    }

    companion object {

        fun newInstance() = newInstance<GenderFragment>()
    }
}