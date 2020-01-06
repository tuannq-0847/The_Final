package com.karl.last_chat

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.karl.last_chat.base.BaseActivity
import com.karl.last_chat.base.StackFragment
import com.karl.last_chat.utils.DialogEnum
import com.karl.last_chat.utils.extensions.replaceFragment
import com.karl.last_chat.view.personal.SharedViewModel
import com.karl.last_chat.view.splash.SplashFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class MainActivity : BaseActivity() {

    private lateinit var sharedViewModel: SharedViewModel

    private val locationManager by lazy { getSystemService(Context.LOCATION_SERVICE) as LocationManager }

    private val viewModel: MainViewModel by viewModel()

    private val permissions =
        arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA
        )

    override val layoutRes: Int
        get() = R.layout.activity_main

    override fun onInitComponents() {
        // supportFragmentManager.addFragment(AuthFragment.newInstance(), R.id.mainContainer)
        supportFragmentManager.replaceFragment(SplashFragment.newInstance(), R.id.mainContainer)
        sharedViewModel = ViewModelProviders.of(this@MainActivity)[SharedViewModel::class.java]
        checkPermission()
    }

    @SuppressLint("MissingPermission")
    private fun getLocations() {
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            1000,
            1F,
            object : LocationListener {
                override fun onLocationChanged(location: Location?) {
                    runOnUiThread {
                        val lat = location?.latitude
                        val long = location?.longitude
                        if (lat != null && long != null) {
                            viewModel.updateLocation(lat, long)
                        }
                    }
                }

                override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                }

                override fun onProviderEnabled(provider: String?) {
                }

                override fun onProviderDisabled(provider: String?) {

                }
            })
    }

//    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
//        val view = currentFocus
//        if (event?.action == MotionEvent.ACTION_DOWN && view is EditText) {
//
//            Log.d("dispatch", "in....")
////            val outRect = Rect()
////            view.getGlobalVisibleRect(outRect)
////            if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
////                view.clearFocus()
////                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
////                imm.hideSoftInputFromWindow(view.windowToken, 0)
////            }
//            return super.dispatchTouchEvent(event)
//        }
//        return super.dispatchTouchEvent(event)
//    }

    private fun checkPermission() {
        if (!isPermissionGranted()) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
                || ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                || ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.CAMERA
                )
            ) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    permissions,
                    1
                )
            }
        } else {
            if (!isLocationEnabled()) {
                showRequestDialogGps()
            } else getLocations()
        }
    }

    private fun isLocationEnabled() =
        locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

    private fun isPermissionGranted(): Boolean {
        return (ContextCompat.checkSelfPermission(
            this,
            permissions[0]
        ) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(
            this,
            permissions[1]
        ) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(
            this, permissions[2]
        ) == PackageManager.PERMISSION_GRANTED
                )
    }

    override fun doObserve() {
        sharedViewModel.eventAvatar.observe(this, Observer {
            when (it) {
                DialogEnum.CAMERA -> openCamera()
                else -> openGallery()
            }
        })
    }


    private fun openGallery() {
        val i = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.INTERNAL_CONTENT_URI
        )
        startActivityForResult(i, ACTIVITY_SELECT_IMAGE, null)
    }

    private fun openCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            FLAG_OPEN_DIALOG_REQUEST -> {
                if (resultCode == Activity.RESULT_CANCELED) {
                    finish()
                }
            }
            REQUEST_IMAGE_CAPTURE -> {
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        val imageBitmap = data?.extras?.get("data") as Bitmap
                        sharedViewModel.bitmapImage.value = imageBitmap

                    } catch (ex: Exception) {
                        ex.printStackTrace()
                    }
                } else if (resultCode == Activity.RESULT_CANCELED) {
                }
            }
            else -> {
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        val imageUri = data?.data
                        sharedViewModel.uriImage.value = imageUri

                    } catch (ex: Exception) {
                        ex.printStackTrace()
                    }
                } else if (resultCode == Activity.RESULT_CANCELED) {
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //allowed
                    showRequestDialogGps()
                } else {
                    //denied
                }
            }
            else -> {

            }
        }
    }

    private fun showRequestDialogGps() {
        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 30 * 1000
            fastestInterval = 5 * 1000
        }
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        builder.setAlwaysShow(true)
        val result = LocationServices.getSettingsClient(this)
            .checkLocationSettings(builder.build())
        result.addOnCompleteListener {
            try {
                it.getResult(ApiException::class.java)
            } catch (ex: ApiException) {
                when (ex.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                        try {
                            val resolvableApiException = ex as ResolvableApiException
                            resolvableApiException
                                .startResolutionForResult(this, FLAG_OPEN_DIALOG_REQUEST)
                        } catch (e: IntentSender.SendIntentException) {

                        }

                    }
                }
            }

        }
    }

    override fun onBackPressed() {
        for (fragment in supportFragmentManager.fragments) {
            if (fragment.isVisible) {
                if (fragment.childFragmentManager.backStackEntryCount > 0) {
                    if (!(fragment.childFragmentManager.fragments.last() as StackFragment).isNeedAutoBackPressed()) {
                        (fragment.childFragmentManager.fragments.last() as StackFragment).onBackPressed()
                        return
                    } else super.onBackPressed()
                } else {
                    if (!(fragment as StackFragment).isNeedAutoBackPressed()) {
                        fragment.onBackPressed()
                        return
                    } else super.onBackPressed()
                }
            }
        }
    }

    companion object {
        const val LOCATION_REFRESH_TIME = 5000
        const val LOCATION_REFRESH_DISTANCE = 5000F
        const val ACTIVITY_SELECT_IMAGE = 1234
        const val FLAG_OPEN_DIALOG_REQUEST = 9999
        const val REQUEST_IMAGE_CAPTURE = 1
    }
}
