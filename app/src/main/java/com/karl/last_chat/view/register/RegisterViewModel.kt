package com.karl.last_chat.view.register

import android.util.Log
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.karl.last_chat.base.BaseViewModel
import com.karl.last_chat.utils.SingleLiveEvent
import com.karl.last_chat.utils.extensions.validEmail
import com.karl.last_chat.utils.extensions.validPassword
import com.karl.last_chat.utils.validate.ValidateEnum
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class RegisterViewModel(private val firebaseAuth: FirebaseAuth) : BaseViewModel() {

    val authResultEvent by lazy { SingleLiveEvent<AuthResult>() }

    val b = mutableListOf<String>()

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
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                // Log.d("result", it.toString())
                authResultEvent.value = it
            }
            .addOnFailureListener {
                error.value = it
            }
    }

    fun getAllIcon() {
        mainScope.launch {
            val url = "https://unicode.org/emoji/charts/full-emoji-list.html"
            val document = withContext(Dispatchers.IO) {
                Jsoup.connect(url).get()
            }
            val elements = document.select("table>tbody>tr")
            Log.d("ele", elements.size.toString())
            elements.forEach {
                val element = it.getElementsByTag("a")
                if (element.text().contains("U+")) {
                    b.add(element.text().replace("U+", "0x"))
                    //  b.add(element.text())
                    Log.d("element", element.text().replace("U+", "0x"))
                }
            }
            b.forEach {
                Log.d("eleb", b.size.toString())
                for (c in StringBuilder().appendCodePoint(Integer.decode(it)).toString().toCharArray()) {
                    Log.d("charArray", "\\u" + Integer.toHexString(c.toInt()))
                }
            }
        }
    }
}
