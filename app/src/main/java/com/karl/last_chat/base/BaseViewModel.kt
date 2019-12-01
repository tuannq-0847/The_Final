package com.karl.last_chat.base

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.karl.last_chat.utils.SingleLiveEvent
import kotlinx.coroutines.*

abstract class BaseViewModel : ViewModel() {


    private val viewModelJob = SupervisorJob()
    open val error by lazy { SingleLiveEvent<Throwable>() }

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
