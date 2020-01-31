package com.karl.last_chat.data.repository.paging

import androidx.paging.PageKeyedDataSource
import com.karl.last_chat.data.model.Message
import com.karl.last_chat.data.repository.AppRepository

class MessageDataSource(private val appRepository: AppRepository) :
    PageKeyedDataSource<Int, Message>() {
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Message>
    ) {
        val currentPage = 1
        val nextPage = currentPage + 1

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Message>) {

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Message>) {

    }
}