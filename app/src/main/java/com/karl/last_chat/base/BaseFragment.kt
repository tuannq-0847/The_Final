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

abstract class BaseFragment<VM : BaseViewModel> : Fragment() {

    abstract val viewModel: VM

    @get:LayoutRes
    abstract val layoutRes: Int

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onInitComponents(view)
        onObserve()
        observeError()
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

    companion object {

        @JvmStatic
        inline fun <reified T : Fragment> newInstance(vararg params: Pair<String, Any>) =
            T::class.java.newInstance().apply {
                arguments = bundleOf(*params)
            }

        const val ARG_DATA = "ARG_DATA"
    }
}
