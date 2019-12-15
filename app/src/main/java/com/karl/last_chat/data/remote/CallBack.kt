package com.karl.last_chat.data.remote

interface CallBack<T> {
    fun onSuccessfully(data: T)
    fun onFailure(throwable: Throwable)
}
