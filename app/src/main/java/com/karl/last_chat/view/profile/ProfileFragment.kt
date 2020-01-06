package com.karl.last_chat.view.profile

import android.location.Geocoder
import android.util.Log
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import com.google.android.material.appbar.AppBarLayout
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseFragment
import com.karl.last_chat.utils.Constants
import com.karl.last_chat.utils.extensions.*
import com.karl.last_chat.view.home.chat.ChatFragment
import com.karl.last_chat.view.profile.detail_image.DetailImageFragment
import kotlinx.android.synthetic.main.fragment_personal.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.math.abs

class ProfileFragment : BaseFragment<ProfileViewModel>(), View.OnClickListener,
    NestedScrollView.OnScrollChangeListener, AppBarLayout.OnOffsetChangedListener {

    private var url = ""
    private var urlAvatar = ""

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        if (abs(verticalOffset) - appBarLayout.totalScrollRange == 0) {
            //collapsed
            view?.visibilityStateViews(imageAvatarSmall, textNameSmall)
        } else {
            //expand
            view?.visibilityStateViews(imageAvatarSmall, textNameSmall, visibilityState = View.GONE)
        }
    }

    override fun onScrollChange(
        v: NestedScrollView?,
        scrollX: Int,
        scrollY: Int,
        oldScrollX: Int,
        oldScrollY: Int
    ) {
        if (scrollY > oldScrollY) {
            Log.d("scrollY", "$scrollY $oldScrollY")
            imageContact.hide()
        } else {
            imageContact.show()
        }
    }

    private var isFriend = false

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.imageBack -> {
                activity?.onBackPressed()
            }
            R.id.imageContact -> {
                if (!isFriend) {
                    uid?.let {
                        viewModel.addFriend(it)
                    }
                } else {
                    fragmentManager?.addFragment(
                        ChatFragment.newInstance(uid!!),
                        R.id.mainContainer
                    )
                }
            }
            R.id.imageBackground -> {
                activity?.supportFragmentManager?.addFragment(
                    DetailImageFragment.newInstance(
                        url = url,
                        signal = Constants.BACKGROUND
                    ),
                    R.id.mainContainer
                )
            }
            R.id.imageAvatar -> {
                activity?.supportFragmentManager?.addFragment(
                    DetailImageFragment.newInstance(
                        url = urlAvatar,
                        signal = Constants.AVATAR
                    ),
                    R.id.mainContainer
                )
            }
        }
    }

    override val viewModel: ProfileViewModel by viewModel()
    override val layoutRes: Int = R.layout.fragment_personal

    private val uid by lazy { arguments?.getString(UID) }

    override fun onInitComponents(view: View) {
        onClickViews(imageBack, imageContact, imageBackground, imageAvatar)
        view.visibilityStateViews(imageContact)
        uid?.let {
            viewModel.getUser(it)
            viewModel.checkIsFriend(it)
            viewModel.checkIsSend(it)
        }
        nestedScrollPersonal.setOnScrollChangeListener(this)
        appBarPersonal.addOnOffsetChangedListener(this)
        imageSetting.visibilityStateViews(imageSetting, visibilityState = View.GONE)
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

    override fun onObserve() {
        viewModel.userData.observe(this, Observer {
            viewModel.hideLoading()
            if (it.pathAvatar.isNotEmpty()) {
                imageAvatar.loadWithGlide(it.pathAvatar)
                imageAvatarSmall.loadWithGlide(it.pathAvatar)
            }
            if (it.pathBackground.isNotEmpty()) {
                imageBackground.loadWithGlide(it.pathBackground, R.drawable.bg_cover_1)
            }
            textNameSmall.text = it.userName
            textName.text = it.userName
            textBio.text = it.bio
            textGender.text = it.gender
            textBirthDay.text = it.birthday!!.getCurrentAge().toString()
            url = it.pathBackground
            urlAvatar = it.pathAvatar
            textGender.setCompoundDrawablesWithIntrinsicBounds(
                if (it.gender == Constants.FEMALE) R.drawable.ic_female else R.drawable.ic_male,
                0,
                0,
                0
            )
            textLocation.text = getLocationName(it.lat, it.long)
        })
        viewModel.isFriendEvent.observe(this, Observer {
            isFriend = it
            if (it) imageContact.setImageResource(R.drawable.ic_chat) else imageContact.setImageResource(
                R.drawable.ic_add_black_24dp
            )
        })
        viewModel.isSendRequest.observe(this, Observer {
            if (it) {
                imageContact.setImageResource(R.drawable.ic_cancel)
            }
        })
    }

    override fun isNeedAutoBackPressed() = false

    companion object {

        fun newInstance(uid: String) = newInstance<ProfileFragment>(Pair(UID, uid))
        private const val UID = "UID"
    }
}
