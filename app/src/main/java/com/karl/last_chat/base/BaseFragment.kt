package com.karl.last_chat.base

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.LayoutRes
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.karl.last_chat.utils.extensions.showDialogWarning
import com.karl.last_chat.view.dialogs.LoadingDialog
import com.karl.last_chat.view.personal.SharedViewModel

abstract class BaseFragment<VM : BaseViewModel> : Fragment(), StackFragment {

    abstract val viewModel: VM

    @get:LayoutRes
    abstract val layoutRes: Int

    private val loadingDialog by lazy { LoadingDialog(context!!) }

    lateinit var sharedViewModel: SharedViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onInitComponents(view)
        sharedViewModel = activity?.run {
            ViewModelProviders.of(this)[SharedViewModel::class.java]
        } ?: throw Exception()
        view.isClickable = true
        view.isFocusableInTouchMode = true
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

    override fun onDestroy() {
        super.onDestroy()
        viewModel.updateStatusOnline(0)
    }


    fun setUpUI(view: View) {
        if (view !is EditText) {
            Log.d("edit", "in....")
            view.setOnTouchListener { v, event ->
                hideKeyboard()
                return@setOnTouchListener false
            }
        }

        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val innerView = view.getChildAt(i)
                setUpUI(innerView)
            }
        }
    }

    private fun hideKeyboard() {
        val inputManager =
            activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.hideLoading()
        hideKeyBoard()
    }

    private fun hideKeyBoard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun onBackPressed() {
    }

    override fun isNeedAutoBackPressed() = true

    fun getOrientation(uri: Uri): Int {
        val cursor = context?.contentResolver?.query(
            uri,
            arrayOf(MediaStore.Images.ImageColumns.ORIENTATION), null, null, null
        )

        if (cursor!!.count != 1) {
            cursor.close()
            return -1
        }

        cursor.moveToFirst()
        val orientation = cursor.getInt(0)
        cursor.close()
        return orientation
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
