package com.karl.last_chat.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.karl.last_chat.utils.extensions.showDialogWarning
import com.karl.last_chat.view.dialogs.LoadingDialog

abstract class BaseFragment<VM : BaseViewModel> : Fragment(),StackFragment {

    abstract val viewModel: VM

    @get:LayoutRes
    abstract val layoutRes: Int

    private val loadingDialog by lazy { LoadingDialog(context!!) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onInitComponents(view)
        observeInternal()
        onObserve()
        observeError()
    }

    private fun observeInternal() {
        viewModel.loading.observe(this, Observer {
            if (it) {
                loadingDialog.show()
            } else {
                loadingDialog.dismiss()
            }
        })
    }

    private fun observeError() {
        viewModel.error.observe(this, Observer {
            viewModel.hideLoading()
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

    abstract fun onObserve()

    open fun onReceiveError(throwable: Throwable) {
        context?.showDialogWarning(throwable.message!!)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.hideLoading()
    }

    override fun onBackPressed() {
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
