package com.karl.last_chat.view.register

import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.karl.last_chat.base.BaseViewModel
import com.karl.last_chat.data.model.User
import com.karl.last_chat.data.repository.AppRepository
import com.karl.last_chat.utils.SingleLiveEvent
import com.karl.last_chat.utils.extensions.validEmail
import com.karl.last_chat.utils.extensions.validPassword
import com.karl.last_chat.utils.validate.ValidateEnum
import kotlinx.coroutines.*
import org.jsoup.Jsoup

class RegisterViewModel(private val authRepository: AppRepository) : BaseViewModel() {

    val authResultEvent by lazy { SingleLiveEvent<Void>() }

    val mainScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    fun validateEmailPassword(
        email: String,
        password: String,
        repeatPassword: String
    ): ValidateEnum {
        if (!email.validEmail()) {
            return ValidateEnum.NOT_MATCH_EMAIL
        } else if (!password.validPassword()) {
            return ValidateEnum.NOT_MATCH_PASSWORD
        } else if (!repeatPassword.validPassword()) {
            return ValidateEnum.NOT_MATCH_PASSWORD
        } else if (password != repeatPassword) {
            return ValidateEnum.PASSWORD_NOT_MATCH_NEW
        }
        return ValidateEnum.COMPLETE_VALIDATE
    }

    fun signUpAccount(email: String, password: String) {
        uiScope.launch {
            showLoading()
            authRepository.createUser(email, password)
                .addOnSuccessListener {
                    insertUser(it.user!!)
                }
                .addOnFailureListener {
                    error.value = it
                }
        }
    }

    private fun insertUser(user: FirebaseUser) {
        uiScope.launch {
            authRepository.insertUser(
                User(user.uid)
            ).addOnSuccessListener {
                authResultEvent.value = it
                hideLoading()
            }.addOnFailureListener {
                error.value = it
            }
        }
    }
}
