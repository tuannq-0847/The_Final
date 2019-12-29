package com.karl.last_chat.view.personal

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.appbar.AppBarLayout
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseFragment
import com.karl.last_chat.utils.extensions.*
import com.karl.last_chat.view.dialogs.DialogAvatar
import kotlinx.android.synthetic.main.fragment_personal.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.abs


class PersonalFragment : BaseFragment<PersonalViewModel>(), View.OnClickListener,
    AppBarLayout.OnOffsetChangedListener {

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        if (abs(verticalOffset) - appBarLayout.totalScrollRange == 0) {
            //collapsed
            view?.visibilityStateViews(imageAvatarSmall, textNameSmall)
        } else {
            //expand
            view?.visibilityStateViews(imageAvatarSmall, textNameSmall, visibilityState = View.GONE)
        }
    }

    private lateinit var sharedViewModel: SharedViewModel

    private val dialog by lazy {
        DialogAvatar(context!!) {
            sharedViewModel.eventAvatar.value = it
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imageAvatar -> {
                dialog.show()
            }
            R.id.imageBackground -> {
                dialog.show()
            }
        }
    }

    override val viewModel: PersonalViewModel by viewModel()
    override val layoutRes: Int
        get() = R.layout.fragment_personal

    override fun onInitComponents(view: View) {
        onClickViews(imageAvatar)
        sharedViewModel = activity?.run {
            ViewModelProviders.of(this)[SharedViewModel::class.java]
        } ?: throw Exception()
        appBarPersonal.addOnOffsetChangedListener(this)
        viewModel.getInforUser()
    }

    override fun onStop() {
        super.onStop()
        dialog.dismiss()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onObserve() {
        sharedViewModel.uriImage.observe(this, Observer { uri ->
            val inputStream = activity?.contentResolver?.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            viewModel.hideLoading()
            inputStream?.let {
                viewModel.uploadAvatar(uri)
                imageAvatar.setImageBitmap(bitmap.rotate(bitmap, getOrientation(uri)))
            }
        })
        viewModel.eventUploadAvatar.observe(this, Observer {
            context?.showMessage("done")
        })
        viewModel.dataPersonal.observe(this, Observer {
            Log.d("pathAvatar", it.pathAvatar)
            viewModel.hideLoading()
            imageAvatar.loadWithGlide(it.pathAvatar)
            imageAvatarSmall.loadWithGlide(it.pathAvatar)
            imageBackground.loadWithGlide(it.pathBackground, R.drawable.bg_cover_1)
            textNameSmall.text = it.userName
        })
    }

    private fun getOrientation(uri: Uri): Int {
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

        fun newInstance() = newInstance<PersonalFragment>()
    }
}