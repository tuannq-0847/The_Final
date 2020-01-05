package com.karl.last_chat.view.register_flow.birthday

import android.view.View
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseFragment
import com.karl.last_chat.data.model.User
import com.karl.last_chat.utils.Constants
import com.karl.last_chat.utils.extensions.addFragment
import com.karl.last_chat.view.register_flow.gender.GenderFragment
import com.karl.last_chat.view.register_flow.parent_res.ParentResFragment
import kotlinx.android.synthetic.main.fragment_birth.*
import kotlinx.android.synthetic.main.fragment_first_name.buttonContinue
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class BirthdayFragment : BaseFragment<BirthdayViewModel>() {
    val user by lazy { arguments?.getParcelable<User>(Constants.USER) }

    override val viewModel: BirthdayViewModel by viewModel()
    override val layoutRes: Int
        get() = R.layout.fragment_birth

    override fun onInitComponents(view: View) {
        datePicker.minDate = -630829096000
        datePicker.maxDate = 1577750400000
        (parentFragment as ParentResFragment).animationProgress(60, 80)
        buttonContinue.setOnClickListener {
            fragmentManager?.addFragment(
                GenderFragment.newInstance(
                    User(
                        userName = user!!.userName,
                        birthday = getDate().time
                    )
                )
            )
        }
    }

    override fun onObserve() {

    }

    private fun getDate(): Date {
        val calendar = Calendar.getInstance()
        calendar.set(datePicker.year, datePicker.month, datePicker.dayOfMonth)
        return calendar.time
    }


    override fun onBackPressed() {
        (parentFragment as ParentResFragment).animationProgress(80, 60)
        fragmentManager?.popBackStack()
    }

    override fun isNeedAutoBackPressed() = false

    companion object {

        fun newInstance(user: User) = newInstance<BirthdayFragment>(Pair(Constants.USER, user))
    }
}