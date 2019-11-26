package com.karl.last_chat.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class BaseViewModel : ViewModel() {


    private val viewModelJob = SupervisorJob()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun handleError(throwable: Throwable) {

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


    fun launchDataLoad() {
        uiScope.launch {
            sortList()
            //modify ui
        }
    }

    suspend fun sortList() = withContext(Dispatchers.Main) {
        //Heavy work
    }
}
