package com.karl.last_chat

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.View
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
import com.karl.last_chat.utils.DialogEnum
import com.karl.last_chat.utils.extensions.replaceFragment
import com.karl.last_chat.view.personal.SharedViewModel
import com.karl.last_chat.view.splash.SplashFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class MainActivity : BaseActivity() {

    private lateinit var sharedViewModel: SharedViewModel

    private val locationManager by lazy { getSystemService(Context.LOCATION_SERVICE) as LocationManager }

    private val viewModel: MainViewModel by viewModel()

    private val permissions =
        arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
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
                    val geocoder = Geocoder(this@MainActivity, Locale.getDefault())
                    val lat = location?.latitude
                    val long = location?.longitude
                    if (lat != null && long != null) {
//                        val address = geocoder.getFromLocation(lat, long, 1)
//                        Log.d("LocationManager", it.getAddressLine(0))
                        viewModel.updateLocation(lat, long)
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

    private fun checkPermission() {
        if (!isPermissionGranted()) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
                || ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
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
            getLocations()
        }
    }

    private fun isPermissionGranted(): Boolean {
        return (ContextCompat.checkSelfPermission(
            this,
            permissions[0]
        ) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(
            this,
            permissions[1]
        ) == PackageManager.PERMISSION_GRANTED)
    }

    override fun doObserve() {
        sharedViewModel.eventAvatar.observe(this, Observer {
            if (it == DialogEnum.CAMERA) {
                openCamera()
            } else {
                openGallery()
            }
        })
        viewModel.loading.observe(this, Observer {
            rotateView.visibility = if (it) View.VISIBLE else View.GONE
        })
    }


    private fun openGallery() {
        val i = Intent(
            Intent.ACTION_PICK,
            android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI
        )
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
                    Log.d("allow", "in...")
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
                                .startResolutionForResult(
                                    this,
                                    1
                                )
                        } catch (e: IntentSender.SendIntentException) {

                        }

                    }
                }
            }

        }
    }

    companion object {
        const val LOCATION_REFRESH_TIME = 5000
        const val LOCATION_REFRESH_DISTANCE = 5000F
        const val ACTIVITY_SELECT_IMAGE = 1234
    }
}
