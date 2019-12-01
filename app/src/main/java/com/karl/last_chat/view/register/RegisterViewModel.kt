package com.karl.last_chat.view.register

import android.util.Log
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.karl.last_chat.base.BaseViewModel
import com.karl.last_chat.utils.SingleLiveEvent
import com.karl.last_chat.utils.extensions.validEmail
import com.karl.last_chat.utils.extensions.validPassword
import com.karl.last_chat.utils.validate.ValidateEnum

class RegisterViewModel(private val firebaseAuth: FirebaseAuth) : BaseViewModel() {

    val authResultEvent by lazy { SingleLiveEvent<AuthResult>() }

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
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                // Log.d("result", it.toString())
                authResultEvent.value = it
            }
            .addOnFailureListener {
                error.value = it
            }
    }
}
