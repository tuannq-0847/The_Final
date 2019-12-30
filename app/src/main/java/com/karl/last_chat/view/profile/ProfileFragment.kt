package com.karl.last_chat.view.profile

import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseFragment
import com.karl.last_chat.utils.extensions.addFragment
import com.karl.last_chat.utils.extensions.loadWithGlide
import com.karl.last_chat.utils.extensions.onClickViews
import com.karl.last_chat.utils.extensions.visibilityStateViews
import com.karl.last_chat.view.home.chat.ChatFragment
import kotlinx.android.synthetic.main.fragment_personal.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment<ProfileViewModel>(), View.OnClickListener {

    private var isFriend = false

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.imageBack -> {
                fragmentManager?.popBackStack()
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
        }
    }

    override val viewModel: ProfileViewModel by viewModel()
    override val layoutRes: Int = R.layout.fragment_personal

    private val uid by lazy { arguments?.getString(UID) }

    override fun onInitComponents(view: View) {
        onClickViews(imageBack, imageContact)
        view.visibilityStateViews(imageContact)
        uid?.let {
            viewModel.getUser(it)
            viewModel.checkIsFriend(it)
        }
    }

    override fun onObserve() {
        viewModel.userData.observe(this, Observer {
            imageAvatar.loadWithGlide(it.pathAvatar)
            imageAvatarSmall.loadWithGlide(it.pathAvatar)
            imageBackground.loadWithGlide(it.pathBackground, R.drawable.bg_cover_1)
            textNameSmall.text = it.userName
        })
        viewModel.isFriendEvent.observe(this, Observer {
            isFriend = it
            Log.d("isFriend", it.toString())
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

    companion object {

        fun newInstance(uid: String) = newInstance<ProfileFragment>(Pair(UID, uid))
        private const val UID = "UID"
    }
}
