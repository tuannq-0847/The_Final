package com.karl.last_chat.view.register

import android.view.View
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseFragment
import com.karl.last_chat.base.BaseViewModel
import com.karl.last_chat.utils.extensions.onClickViews
import com.karl.last_chat.utils.extensions.showMessage
import com.karl.last_chat.utils.validate.ValidateEnum
import kotlinx.android.synthetic.main.fragment_register.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : BaseFragment<RegisterViewModel>(), View.OnClickListener {
    override val viewModel: RegisterViewModel by viewModel()
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.textSignUp -> {
                handleButton()
            }
        }
    }

    override val layoutRes: Int
        get() = R.layout.fragment_register

    override fun onInitComponents(view: View) {
        onClickViews(textSignUp)
    }

    fun handleButton() {
        val validate = viewModel.validateEmailPassword(
            editEmail.text.toString(),
            editPassword.text.toString(),
            editRepeatPassword.text.toString()
        )
        val pair = validate.getSignal()
        if (validate != ValidateEnum.COMPLETE_VALIDATE) context?.showMessage(pair.second)
        else {
            viewModel.signUpAccount(
                editEmail.text.toString(),
                editPassword.text.toString()
            )
        }
    }

    companion object {

        fun newInstance() = newInstance<RegisterFragment>()
    }

}