package com.karl.last_chat.view.profile.edit

import android.app.DatePickerDialog
import android.view.View
import androidx.lifecycle.Observer
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseFragment
import com.karl.last_chat.utils.extensions.getCurrentAge
import com.karl.last_chat.utils.extensions.onClickViews
import kotlinx.android.synthetic.main.fragment_edit_personal.*
import kotlinx.android.synthetic.main.toolbar_profile.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class EditProfileFragment : BaseFragment<EditProfileViewModel>(), View.OnClickListener {

    private val myCalendar = Calendar.getInstance()

    private val datePickerProfile by lazy {
        DatePickerDialog(
            context!!, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.editAgeProfile -> {
                datePickerProfile.run {
                    datePicker.minDate = -630829096000
                    datePicker.maxDate = 1577750400000
                    show()
                }
            }
            R.id.imageDone -> {
                viewModel.updateInforUser(
                    editBioEditUser.text.toString(),
                    editNameProfile.text.toString(),
                    getDate().time,
                    if (radioMaleEditUserProfile.isChecked) "Male" else "Female"
                )
            }
            R.id.imageBackProfile -> {
                activity?.onBackPressed()
            }
        }
    }

    var date: DatePickerDialog.OnDateSetListener =
        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            editAgeProfile.text = getDate().time.getCurrentAge().toString()
        }

    override val viewModel: EditProfileViewModel by viewModel()
    override val layoutRes: Int = R.layout.fragment_edit_personal

    override fun onInitComponents(view: View) {
        viewModel.getInformationUser()
        onClickViews(editAgeProfile, imageBackProfile, imageDone)
    }

    private fun getDate(): Date {
        val calendar = Calendar.getInstance()
        calendar.set(
            datePickerProfile.datePicker.year,
            datePickerProfile.datePicker.month,
            datePickerProfile.datePicker.dayOfMonth
        )
        return calendar.time
    }

    override fun onObserve() {
        viewModel.dataUser.observe(this, Observer {
            editNameProfile.setText(it.userName)
            editAgeProfile.text = it.birthday!!.getCurrentAge().toString()
            editBioEditUser.setText(it.bio)
            when (it.gender.toString()) {
                "Male" -> radioMaleEditUserProfile.isChecked = true
                "Female" -> radioFemaleEditUserProfile.isChecked = true
            }
        })
        viewModel.inforUser.observe(this, Observer {
            activity?.onBackPressed()
        })
    }

    companion object {

        fun newInstance() = newInstance<EditProfileFragment>()
    }
}