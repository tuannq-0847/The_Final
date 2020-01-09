package com.karl.last_chat.view.personal

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Geocoder
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import com.google.android.material.appbar.AppBarLayout
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseFragment
import com.karl.last_chat.utils.Constants
import com.karl.last_chat.utils.DialogEnum
import com.karl.last_chat.utils.extensions.*
import com.karl.last_chat.view.auth.AuthFragment
import com.karl.last_chat.view.dialogs.DialogAvatar
import com.karl.last_chat.view.dialogs.DialogSetting
import com.karl.last_chat.view.profile.detail_image.DetailImageFragment
import com.karl.last_chat.view.profile.edit.EditProfileFragment
import kotlinx.android.synthetic.main.fragment_personal.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*
import kotlin.math.abs


class PersonalFragment : BaseFragment<PersonalViewModel>(), View.OnClickListener,
    AppBarLayout.OnOffsetChangedListener {

    private var signalImage = ""
    private var pathImage = ""
    private var pathBackground = ""

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        if (abs(verticalOffset) - appBarLayout.totalScrollRange == 0) {
            //collapsed
            view?.visibilityStateViews(imageAvatarSmall, textNameSmall)
        } else {
            //expand
            view?.visibilityStateViews(imageAvatarSmall, textNameSmall, visibilityState = View.GONE)
        }
    }

    private val dialog by lazy {
        DialogAvatar(context!!) {
            listener(it)
        }
    }

    private fun listener(dialogEnum: DialogEnum) {
        if (dialogEnum != DialogEnum.STORED)
            sharedViewModel.eventAvatar.value = dialogEnum
        else {
            dialog.dismiss()
            activity?.supportFragmentManager?.addFragment(
                DetailImageFragment.newInstance(
                    url = if (signalImage == Constants.AVATAR) pathImage else pathBackground,
                    signal = signalImage
                ),
                R.id.mainContainer
            )
        }
    }

    private val dialogSetting by lazy {
        DialogSetting(context!!, "") {
            if (it)
                viewModel.updateStatusOnline(0)
            else {
                activity?.supportFragmentManager?.addFragment(EditProfileFragment.newInstance())
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imageAvatar -> {
                dialog.show()
                dialog.setTitle(Constants.AVATAR)
                signalImage = Constants.AVATAR
            }
            R.id.imageBackground -> {
                dialog.show()
                dialog.setTitle(Constants.BACKGROUND)
                signalImage = Constants.BACKGROUND
            }
            R.id.imageSetting -> {
                dialogSetting.show()
            }
        }
    }

    override val viewModel: PersonalViewModel by viewModel()
    override val layoutRes: Int
        get() = R.layout.fragment_personal

    override fun onInitComponents(view: View) {
        onClickViews(imageAvatar, imageSetting, imageBackground)
        appBarPersonal.addOnOffsetChangedListener(this)
        viewModel.getInforUser()
        imageBack.visibilityStateViews(imageBack, imageContact, visibilityState = View.GONE)
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
                if (signalImage == Constants.AVATAR) {
                    imageAvatar.loadWithGlideBitmap(bitmap.rotate(bitmap, getOrientation(uri)))
                    viewModel.uploadAvatar(uri)
                } else {
                    imageBackground.loadWithGlideBitmap(
                        bitmap.rotate(bitmap, getOrientation(uri)),
                        R.drawable.bg_cover_1
                    )
                    viewModel.uploadBackground(uri)
                }
            }
        })
        sharedViewModel.bitmapImage.observe(this, Observer {
            viewModel.hideLoading()
            val baos = ByteArrayOutputStream()
            it.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()
            if (signalImage == Constants.AVATAR) {
                imageAvatar.loadWithGlideBitmap(it)
                viewModel.putByteAvatar(data)
            } else {
                imageBackground.loadWithGlideBitmap(
                    it,
                    R.drawable.bg_cover_1
                )
                viewModel.putByteBackground(data)
            }
        })
        viewModel.eventUploadAvatar.observe(this, Observer {
            viewModel.hideLoading()
            context?.showMessage("done")
        })
        viewModel.dataPersonal.observe(this, Observer {
            viewModel.hideLoading()
            imageAvatar.loadWithGlide(it.pathAvatar)
            imageAvatarSmall.loadWithGlide(it.pathAvatar)
            pathImage = it.pathAvatar
            pathBackground = it.pathBackground
            imageBackground.loadWithGlide(it.pathBackground, R.drawable.bg_cover_1)
            textNameSmall.text = it.userName
            textName.text = it.userName
            textBio.text = it.bio
            textGender.text = it.gender
            textBirthDay.text = it.birthday!!.getCurrentAge().toString()
            textLocation.text = if (getLocationName(
                    it.lat,
                    it.long
                ).isEmpty()
            ) "Số 3 Đường Cầu Giấy, Ngọc Khánh, Đống Đa, Hà Nội, Việt Nam" else getLocationName(
                it.lat,
                it.long
            )
        })
        viewModel.eventStatus.observe(this, Observer {
            viewModel.logout()
            activity?.supportFragmentManager?.replaceFragment(
                AuthFragment.newInstance(),
                R.id.mainContainer
            )
            clearApplicationData()
        })
    }

    fun clearApplicationData() {
        val cacheDirectory = activity!!.cacheDir
        val applicationDirectory = File(cacheDirectory.parent)
        if (applicationDirectory.exists()) {
            val fileNames = applicationDirectory.list()
            for (fileName in fileNames) {
                if (fileName != "lib") {
                    deleteFile(File(applicationDirectory, fileName))
                }
            }
        }
    }

    fun deleteFile(file: File?): Boolean {
        var deletedAll = true
        if (file != null) {
            if (file.isDirectory) {
                val children = file.list()
                for (i in children.indices) {
                    deletedAll = deleteFile(File(file, children[i])) && deletedAll
                }
            } else {
                deletedAll = file.delete()
            }
        }

        return deletedAll
    }

    private fun getLocationName(lat: Double, long: Double): String {
        context?.let {
            val addressList = Geocoder(it, Locale.getDefault())
                .getFromLocation(lat, long, 1)
            if (!addressList.isNullOrEmpty()) {
                return addressList[0].getAddressLine(0)
            }
        }
        return ""
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dialog.dismiss()
    }

    companion object {

        fun newInstance() = newInstance<PersonalFragment>()
    }
}