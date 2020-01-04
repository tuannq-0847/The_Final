package com.karl.last_chat.view.register_flow.birthday

import android.view.View
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseFragment
import com.karl.last_chat.utils.extensions.addFragment
import com.karl.last_chat.view.register_flow.gender.GenderFragment
import com.karl.last_chat.view.register_flow.parent_res.ParentResFragment
import kotlinx.android.synthetic.main.fragment_first_name.*
import kotlinx.android.synthetic.main.layout_parent_register.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class BirthdayFragment : BaseFragment<BirthdayViewModel>() {
    override val viewModel: BirthdayViewModel by viewModel()
    override val layoutRes: Int
        get() = R.layout.fragment_birth

    override fun onInitComponents(view: View) {
        (parentFragment as ParentResFragment).animationProgress(60, 80)
    }

    override fun onObserve() {
        buttonContinue.setOnClickListener {
            fragmentManager?.addFragment(GenderFragment.newInstance(), R.id.frameParentRegister)
        }
    }


    override fun onBackPressed() {
        (parentFragment as ParentResFragment).animationProgress(80, 60)
        fragmentManager?.popBackStack()
    }

    override fun isNeedAutoBackPressed() = false

    companion object {

        fun newInstance() = newInstance<BirthdayFragment>()
    }
}