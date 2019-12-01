package com.karl.last_chat.view.login

import android.view.View
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseFragment
import com.karl.last_chat.base.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment<LoginViewModel>() {
    override val viewModel: LoginViewModel by viewModel()
    override val layoutRes: Int
        get() = R.layout.fragment_login

    override fun onInitComponents(view: View) {

    }

    companion object {

        fun newInstance() = newInstance<LoginFragment>()
    }
}
