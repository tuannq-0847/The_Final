package com.karl.last_chat.utils.validate

import com.karl.last_chat.App
import com.karl.last_chat.R
import com.karl.last_chat.utils.Constants

enum class ValidateEnum {
    NOT_MATCH_EMAIL {
        override fun getSignal(): Pair<ValidateEnum, String> {
            return Pair(NOT_MATCH_EMAIL, getContext().getString(R.string.email_not_valid))
        }

    },
    NOT_MATCH_PASSWORD {
        override fun getSignal(): Pair<ValidateEnum, String> {
            return Pair(NOT_MATCH_PASSWORD, getContext().getString(R.string.password_not_valid))
        }

    },
    PASSWORD_NOT_MATCH_NEW {
        override fun getSignal(): Pair<ValidateEnum, String> {
            return Pair(
                PASSWORD_NOT_MATCH_NEW,
                getContext().getString(R.string.pw_new_pw_not_match)
            )
        }

    },
    COMPLETE_VALIDATE {
        override fun getSignal(): Pair<ValidateEnum, String> {
            return Pair(COMPLETE_VALIDATE, Constants.EMPTY)
        }

    };

    abstract fun getSignal(): Pair<ValidateEnum, String>
    fun getContext() = App.context.resources
}
