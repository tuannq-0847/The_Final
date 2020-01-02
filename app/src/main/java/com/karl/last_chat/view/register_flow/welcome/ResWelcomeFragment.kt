package com.karl.last_chat.view.register_flow.welcome

import android.util.Log
import android.view.View
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseFragment
import com.karl.last_chat.utils.extensions.addFragment
import com.karl.last_chat.view.register_flow.first_name.MyFirstNameFragment
import com.karl.last_chat.view.register_flow.parent_res.ParentResFragment
import kotlinx.android.synthetic.main.layout_parent_register.*
import kotlinx.android.synthetic.main.layout_welcome.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ResWelcomeFragment : BaseFragment<ResWelcomeViewModel>() {
    override val viewModel: ResWelcomeViewModel by viewModel()
    override val layoutRes: Int
        get() = R.layout.layout_welcome

    override fun onInitComponents(view: View) {
        buttonGot.setOnClickListener {
            fragmentManager?.addFragment(
                MyFirstNameFragment.newInstance(),
                R.id.frameParentRegister
            )
        }
        (parentFragment as ParentResFragment).frameProgress.progress = 20
    }

    override fun onObserve() {
    }


    override fun onBackPressed() {

    }

    companion object {

        fun newInstance() = newInstance<ResWelcomeFragment>()
    }
}
