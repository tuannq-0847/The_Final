package com.karl.last_chat.data.remote

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class GenericError(val code: Int? = null, val error: ErrorResponse? = null) :
        ResultWrapper<Nothing>()
}

fun <T, DATA> ResultWrapper<T>.getData(

    onSuccess: (data: T) -> DATA,
    onFail: (code: Int?, error: ErrorResponse?) -> DATA
): DATA = when (this) {
    is ResultWrapper.Success -> onSuccess(this.value)
    is ResultWrapper.GenericError -> onFail(this.code, this.error)
}
