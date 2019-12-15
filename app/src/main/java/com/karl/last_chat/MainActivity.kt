package com.karl.last_chat

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.karl.last_chat.base.BaseActivity
import com.karl.last_chat.utils.DialogEnum
import com.karl.last_chat.utils.extensions.replaceFragment
import com.karl.last_chat.view.personal.PersonalViewModel
import com.karl.last_chat.view.personal.SharedViewModel
import com.karl.last_chat.view.splash.SplashFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Exception

class MainActivity : BaseActivity() {

    private lateinit var sharedViewModel: SharedViewModel

    override val layoutRes: Int
        get() = R.layout.activity_main

    override fun onInitComponents() {
        // supportFragmentManager.addFragment(AuthFragment.newInstance(), R.id.mainContainer)
        supportFragmentManager.replaceFragment(SplashFragment.newInstance(), R.id.mainContainer)
        sharedViewModel = ViewModelProviders.of(this@MainActivity)[SharedViewModel::class.java]
    }

    override fun doObserve() {
        sharedViewModel.eventAvatar.observe(this, Observer {
            if (it == DialogEnum.CAMERA) {
                openCamera()
            } else {
                openGallery()
            }
        })
    }


    private fun openGallery() {
        val i = Intent(
            Intent.ACTION_PICK,
            android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI
        )
        val ACTIVITY_SELECT_IMAGE = 1234
        startActivityForResult(i, ACTIVITY_SELECT_IMAGE, null)
    }

    private fun openCamera() {
        val intent = Intent("android.media.action.IMAGE_CAPTURE")
        startActivity(intent, null)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            try {
                val imageUri = data?.data
                sharedViewModel.uriImage.value = imageUri

            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

}
