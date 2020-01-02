package com.karl.last_chat.view.register_flow.parent_res

import android.view.View
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseFragment
import com.karl.last_chat.utils.extensions.addFragment
import com.karl.last_chat.utils.extensions.addFragmentWithBackStack
import com.karl.last_chat.utils.extensions.replaceFragment
import com.karl.last_chat.view.register_flow.welcome.ResWelcomeFragment
import kotlinx.android.synthetic.main.layout_parent_register.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ParentResFragment : BaseFragment<ParentResViewModel>() {
    override val viewModel: ParentResViewModel by viewModel()
    override val layoutRes: Int
        get() = R.layout.layout_parent_register

    override fun onInitComponents(view: View) {
        childFragmentManager.replaceFragment(
            ResWelcomeFragment.newInstance(),
            R.id.frameParentRegister
        )
        frameProgress.progress = 20
    }

    override fun onObserve() {
    }

    companion object {

        fun newInstance() = newInstance<ParentResFragment>()
    }

}