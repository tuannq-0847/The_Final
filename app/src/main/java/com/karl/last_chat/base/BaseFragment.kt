package com.karl.last_chat.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.karl.last_chat.utils.extensions.showDialogWarning
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

abstract class BaseFragment<VM : BaseViewModel> : Fragment() {

    abstract val viewModel: VM

    @get:LayoutRes
    abstract val layoutRes: Int

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onInitComponents(view)
        observeError()
    }

    private fun observeError() {
        viewModel.error.observe(this, Observer {
            onReceiveError(it)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutRes, container, false)
    }

    abstract fun onInitComponents(view: View)

    open fun onReceiveError(throwable: Throwable) {
        context?.showDialogWarning(throwable.message!!)
    }

    companion object {

        @JvmStatic
        inline fun <reified T : Fragment> newInstance(vararg params: Pair<String, Any>) =
            T::class.java.newInstance().apply {
                arguments = bundleOf(*params)
            }

        const val ARG_DATA = "ARG_DATA"
    }
}
