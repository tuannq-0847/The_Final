package com.karl.last_chat.view.login

import android.app.Application
import com.google.firebase.auth.AuthResult
import com.karl.last_chat.base.BaseViewModel
import com.karl.last_chat.data.repository.AppRepository
import com.karl.last_chat.utils.SingleLiveEvent
import com.karl.last_chat.utils.extensions.validEmail
import com.karl.last_chat.utils.extensions.validPassword
import com.karl.last_chat.utils.validate.ValidateEnum
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LoginViewModel(private val appRepository: AppRepository,application: Application) : BaseViewModel(appRepository,application) {

    val authResultEvent by lazy { SingleLiveEvent<AuthResult>() }
    fun validateLogin(
        email: String,
        password: String
    ): ValidateEnum {
        if (!email.validEmail()) {
            return ValidateEnum.NOT_MATCH_EMAIL
        } else if (!password.validPassword()) {
            return ValidateEnum.NOT_MATCH_PASSWORD
        }
        return ValidateEnum.COMPLETE_VALIDATE
    }

    fun loginWithEmailPassword(email: String, password: String) {
        runBlocking {
            showLoading()
            appRepository.signIn(email, password)
                .addOnSuccessListener {
                    authResultEvent.value = it
                }
                .addOnFailureListener {
                    error.value = it
                }
        }
    }
}
