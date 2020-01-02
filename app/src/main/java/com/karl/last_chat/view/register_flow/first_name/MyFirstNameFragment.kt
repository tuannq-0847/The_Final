package com.karl.last_chat.view.register_flow.first_name

import android.util.Log
import android.view.View
import androidx.core.widget.doAfterTextChanged
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseFragment
import com.karl.last_chat.utils.extensions.addFragment
import com.karl.last_chat.view.register_flow.birthday.BirthdayFragment
import com.karl.last_chat.view.register_flow.parent_res.ParentResFragment
import kotlinx.android.synthetic.main.fragment_first_name.*
import kotlinx.android.synthetic.main.layout_parent_register.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyFirstNameFragment : BaseFragment<MyFirstNameViewModel>() {
    override val viewModel: MyFirstNameViewModel by viewModel()
    override val layoutRes: Int
        get() = R.layout.fragment_first_name

    override fun onInitComponents(view: View) {
        (parentFragment as ParentResFragment).frameProgress.progress = 60
        editName.doAfterTextChanged {
            buttonContinue.isEnabled = it!!.isNotEmpty()
        }
    }

    override fun onObserve() {
        buttonContinue.setOnClickListener {
            fragmentManager?.addFragment(BirthdayFragment.newInstance(), R.id.frameParentRegister)
        }
    }

    override fun onBackPressed() {
        Log.d("onBack", "in...")
        (parentFragment as ParentResFragment).frameProgress.progress = 20
        fragmentManager?.popBackStack()
    }

    companion object {

        fun newInstance() = newInstance<MyFirstNameFragment>()
    }
}