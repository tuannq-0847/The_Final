package com.karl.last_chat.view.register

import android.content.Context
import android.view.View
import androidx.lifecycle.Observer
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseFragment
import com.karl.last_chat.utils.Constants
import com.karl.last_chat.utils.extensions.onClickViews
import com.karl.last_chat.utils.extensions.replaceFragment
import com.karl.last_chat.utils.extensions.showDialogOk
import com.karl.last_chat.utils.extensions.showMessage
import com.karl.last_chat.utils.validate.ValidateEnum
import com.karl.last_chat.view.home.HomeFragment
import com.karl.last_chat.view.register_flow.parent_res.ParentResFragment
import kotlinx.android.synthetic.main.fragment_register.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : BaseFragment<RegisterViewModel>(), View.OnClickListener {
    override fun onObserve() {
        viewModel.authResultEvent.observe(this, Observer {
            context?.showDialogOk(getString(R.string.res_successfully)) {
                // viewModel.getAllIcon()
                fragmentManager?.replaceFragment(
                    ParentResFragment.newInstance(),
                    R.id.mainContainer
                )
                writeSharePreferences(false)
                viewModel.updateFirebaseIntanceId()
            }
        })
    }

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

    private fun handleButton() {
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


    private fun writeSharePreferences(isFinish: Boolean) {
        val sharePreferences =
            activity?.getSharedPreferences(Constants.KEY_IS_FINISHED, Context.MODE_PRIVATE)
        val editor = sharePreferences?.edit()
        editor?.putBoolean(Constants.KEY_IS_FINISHED, false)
        editor?.apply()
    }

    companion object {

        fun newInstance() = newInstance<RegisterFragment>()
    }

}