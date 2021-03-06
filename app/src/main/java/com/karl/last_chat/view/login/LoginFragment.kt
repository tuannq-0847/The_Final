package com.karl.last_chat.view.login

import android.view.View
import androidx.lifecycle.Observer
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseFragment
import com.karl.last_chat.utils.extensions.onClickViews
import com.karl.last_chat.utils.extensions.replaceFragment
import com.karl.last_chat.utils.extensions.showMessage
import com.karl.last_chat.utils.validate.ValidateEnum
import com.karl.last_chat.view.dialogs.ForgotPwDialog
import com.karl.last_chat.view.home.HomeFragment
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment<LoginViewModel>(), View.OnClickListener {

    private val dialogPw by lazy {
        ForgotPwDialog(context!!) {
            viewModel.sendMailForgotPw(it)
        }
    }

    override fun onObserve() {
        viewModel.authResultEvent.observe(this, Observer {
            viewModel.hideLoading()
            fragmentManager?.replaceFragment(HomeFragment.newInstance(), R.id.mainContainer)
            viewModel.updateFirebaseIntanceId()
            viewModel.updateStatusOnline(1)
        })
        viewModel.isSentEmail.observe(this, Observer {
            context?.showMessage("Sent mail successfully")
            dialogPw.dismiss()
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.textLogin -> {
                val validate = viewModel.validateLogin(
                    editEmailL.text.toString(),
                    editPasswordL.text.toString()
                )
                val pair = validate.getSignal()
                if (validate != ValidateEnum.COMPLETE_VALIDATE) context?.showMessage(pair.second)
                viewModel.loginWithEmailPassword(
                    editEmailL.text.toString(),
                    editPasswordL.text.toString()
                )
            }
            R.id.textForgot -> {
                dialogPw.show()
            }
        }
    }

    override val viewModel: LoginViewModel by viewModel()
    override val layoutRes: Int
        get() = R.layout.fragment_login

    override fun onInitComponents(view: View) {
        onClickViews(textLogin, textForgot)
    }

    companion object {

        fun newInstance() = newInstance<LoginFragment>()
    }
}
