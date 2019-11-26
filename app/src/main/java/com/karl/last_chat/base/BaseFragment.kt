package com.karl.last_chat.base

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    @get:LayoutRes
    abstract val layoutRes: Int

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onInitComponents(view)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutRes, container, false)
    }

    abstract fun onInitComponents(view: View)

    companion object {

        @JvmStatic
        fun <T> newInstance(fragment: Fragment, data: T) = fragment.apply {
            arguments = Bundle().apply {
                putParcelable(ARG_DATA,data as Parcelable)
            }
        }

        const val ARG_DATA = "ARG_DATA"
    }
}
