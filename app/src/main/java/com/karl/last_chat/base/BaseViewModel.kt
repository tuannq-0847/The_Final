package com.karl.last_chat.base

import androidx.lifecycle.ViewModel
import com.karl.last_chat.utils.SingleLiveEvent
import kotlinx.coroutines.*

abstract class BaseViewModel : ViewModel() {


    open val viewModelJob = SupervisorJob()
    open val error by lazy { SingleLiveEvent<Throwable>() }
    open val loading by lazy { SingleLiveEvent<Boolean>() }

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


    open val listEmojis = mutableListOf(
        "\ud83d",
        "\ude00",
        "\ud83d",
        "\ude03",
        "\ud83d",
        "\ude04",
        "\ud83d",
        "\ude01",
        "\ud83d",
        "\ude06",
        "\ud83d",
        "\ude05",
        "\ud83e",
        "\udd23",
        "\ud83d",
        "\ude02",
        "\ud83d",
        "\ude42",
        "\ud83d",
        "\ude43",
        "\ud83d",
        "\ude09",
        "\ud83d",
        "\ude0a",
        "\ud83d",
        "\ude07",
        "\ud83e",
        "\udd70",
        "\ud83d",
        "\ude0d",
        "\ud83e",
        "\udd29",
        "\ud83d",
        "\ude18",
        "\ud83d",
        "\ude17",
        "\u263a",
        "\ud83d",
        "\ude1a",
        "\ud83d",
        "\ude19",
        "\ud83d",
        "\ude0b",
        "\ud83d",
        "\ude1b",
        "\ud83d",
        "\ude1c",
        "\ud83e",
        "\udd2a",
        "\ud83d",
        "\ude1d",
        "\ud83e",
        "\udd11",
        "\ud83e",
        "\udd17",
        "\ud83e",
        "\udd2d",
        "\ud83e",
        "\udd2b",
        "\ud83e",
        "\udd14",
        "\ud83e",
        "\udd10",
        "\ud83e",
        "\udd28",
        "\ud83d",
        "\ude10",
        "\ud83d",
        "\ude11",
        "\ud83d",
        "\ude36",
        "\ud83d",
        "\ude0f",
        "\ud83d",
        "\ude12",
        "\ud83d",
        "\ude44",
        "\ud83d",
        "\ude2c",
        "\ud83e",
        "\udd25",
        "\ud83d",
        "\ude0c",
        "\ud83d",
        "\ude14",
        "\ud83d",
        "\ude2a",
        "\ud83e",
        "\udd24",
        "\ud83d",
        "\ude34",
        "\ud83d",
        "\ude37",
        "\ud83e",
        "\udd12",
        "\ud83e",
        "\udd2f"
    )

    suspend fun sortList() = withContext(Dispatchers.Main) {
        //Heavy work
    }
}
