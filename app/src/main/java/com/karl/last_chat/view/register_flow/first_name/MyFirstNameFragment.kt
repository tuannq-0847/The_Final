package com.karl.last_chat.view.register_flow.first_name

import android.view.View
import androidx.core.widget.doAfterTextChanged
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseFragment
import com.karl.last_chat.data.model.User
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
        (parentFragment as ParentResFragment).run {
            animationProgress(20, 60)
            imageParent.setImageResource(R.drawable.ic_arrow_black)
        }
        editName.doAfterTextChanged {
            buttonContinue.isEnabled = !it.isNullOrEmpty()
        }
    }

    override fun onObserve() {
        buttonContinue.isEnabled = false
        buttonContinue.setOnClickListener {
            fragmentManager?.addFragment(
                BirthdayFragment.newInstance(User(userName = editName.text.toString())),
                R.id.frameParentRegister
            )
        }
    }

    override fun onBackPressed() {
        (parentFragment as ParentResFragment).run {
            animationProgress(60, 20)
            imageParent.setImageResource(R.drawable.ic_mark)
        }
        fragmentManager?.popBackStack()
    }

    override fun isNeedAutoBackPressed() = false

    companion object {

        fun newInstance() = newInstance<MyFirstNameFragment>()
    }
}