package com.karl.last_chat.view.profile.detail_image

import android.view.View
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseFragment
import com.karl.last_chat.utils.extensions.loadWithGlide
import kotlinx.android.synthetic.main.fragment_detail_image.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailImageFragment : BaseFragment<DetailImageViewModel>() {
    override val viewModel: DetailImageViewModel by viewModel()
    override val layoutRes: Int
        get() = R.layout.fragment_detail_image

    private val url by lazy { arguments?.getString(URL) ?: "" }
    private val signal by lazy { arguments?.getString(SIGNAL) ?: "" }

    override fun onInitComponents(view: View) {
        photoDetail.loadWithGlide(
            url,
            if (signal == "Avatar") R.drawable.avatar else R.drawable.bg_cover_1
        )
    }

    override fun onObserve() {
    }

    companion object {

        fun newInstance(url: String, signal: String) =
            newInstance<DetailImageFragment>(Pair(URL, url), Pair(SIGNAL, signal))

        const val URL = "URL"
        const val SIGNAL = "SIGNAL"
    }
}