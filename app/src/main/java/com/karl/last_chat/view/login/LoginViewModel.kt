package com.karl.last_chat.view.login

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.karl.last_chat.base.BaseViewModel
import com.karl.last_chat.utils.SingleLiveEvent
import com.karl.last_chat.utils.extensions.validEmail
import com.karl.last_chat.utils.extensions.validPassword
import com.karl.last_chat.utils.validate.ValidateEnum

class LoginViewModel(private val firebaseAuth: FirebaseAuth) : BaseViewModel() {

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
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                authResultEvent.value = it
            }
            .addOnFailureListener {
                error.value = it
            }
    }
}
