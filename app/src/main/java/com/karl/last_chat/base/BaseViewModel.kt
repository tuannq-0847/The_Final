package com.karl.last_chat.base

import androidx.lifecycle.ViewModel
import com.karl.last_chat.utils.SingleLiveEvent
import kotlinx.coroutines.*

abstract class BaseViewModel : ViewModel() {


    open val viewModelJob = SupervisorJob()
    open val error by lazy { SingleLiveEvent<Throwable>() }
    open val loading by lazy { SingleLiveEvent<Boolean>() }

    val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

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
        "\ud83d\ude22",
        "\ud83d\ude25",
        "\ud83e\udd24",
        "\ud83d\ude2d",
        "\ud83d\ude13",
        "\ud83d\ude2a",
        "\ud83d\ude34",
        "\ud83d\ude44",
        "\ud83e\udd14",
        "\ud83e\udd25",
        "\ud83d\ude2c",
        "\ud83e\udd10",
        "\ud83e\udd22",
        "\ud83e\udd27",
        "\ud83d\ude37",
        "\ud83e\udd12",
        "\ud83e\udd15",
        "\ud83d\ude08",
        "\ud83d\udc7f",
        "\ud83d\udc79",
        "\ud83d\udc7a",
        "\ud83d\udca9",
        "\ud83d\udc7b",
        "\ud83d\udc80",
        "\u2620\ufe0f",
        "\ud83d\udc7d",
        "\ud83d\udc7e",
        "\ud83e\udd16",
        "\ud83c\udf83",
        "\ud83d\ude3a",
        "\ud83d\ude38",
        "\ud83d\ude39",
        "\ud83d\ude3b",
        "\ud83d\ude3c",
        "\ud83d\ude3d",
        "\ud83d\ude40",
        "\ud83d\ude3f",
        "\ud83d\ude3e",
        "\ud83d\udc50",
        "\ud83d\ude4c",
        "\ud83d\udc4f",
        "\ud83d\ude4f",
        "\ud83e\udd1d",
        "\ud83d\udc4d",
        "\ud83d\udc4e",
        "\ud83d\udc4a",
        "\u270a",
        "\ud83e\udd1b",
        "\ud83e\udd1c",
        "\ud83e\udd1e",
        "\u270c\ufe0f",
        "\ud83e\udd18",
        "\ud83d\udc4c",
        "\ud83d\udc48",
        "\ud83d\udc49",
        "\ud83d\udc46",
        "\ud83d\udc47",
        "\u261d\ufe0f",
        "\u270b",
        "\ud83e\udd1a",
        "\ud83d\udd90",
        "\ud83d\udd96",
        "\ud83d\udc4b",
        "\ud83e\udd19",
        "\ud83d\udcaa",
        "\ud83d\udd95",
        "\u270d\ufe0f",
        "\ud83e\udd33",
        "\ud83d\udc85",
        "\ud83d\udc8d",
        "\ud83d\udc84",
        "\ud83d\udc8b",
        "\ud83d\udc44",
        "\ud83d\udc45",
        "\ud83d\udc42",
        "\ud83d\udc43",
        "\ud83d\udc63",
        "\ud83d\udc41",
        "\ud83d\udc40",
        "\ud83d\udde3",
        "\ud83d\udc64",
        "\ud83d\udc65",
        "\ud83d\udc76",
        "\ud83d\udc66",
        "\ud83d\udc67",
        "\ud83d\udc68",
        "\ud83d\udc69",
        "\ud83d\udc71\u200d\u2640",
        "\ud83d\udc71",
        "\ud83d\udc74",
        "\ud83d\udc75",
        "\ud83d\udc72",
        "\ud83d\udc73\u200d\u2640",
        "\ud83d\udc73",
        "\ud83d\udc6e\u200d\u2640",
        "\ud83d\udc6e",
        "\ud83d\udc77\u200d\u2640",
        "\ud83d\udc77",
        "\ud83d\udc82\u200d\u2640",
        "\ud83d\udc82",
        "\ud83d\udd75\ufe0f\u200d\u2640\ufe0f",
        "\ud83d\udd75",
        "\ud83d\udc69\u200d\u2695",
        "\ud83d\udc68\u200d\u2695",
        "\ud83d\udc69\u200d\ud83c\udf3e",
        "\ud83d\udc68\u200d\ud83c\udf3e",
        "\ud83d\udc69\u200d\ud83c\udf73",
        "\ud83d\udc68\u200d\ud83c\udf73",
        "\ud83d\udc69\u200d\ud83c\udf93",
        "\ud83d\udc68\u200d\ud83c\udf93",
        "\ud83d\udc69\u200d\ud83c\udfa4",
        "\ud83d\udc68\u200d\ud83c\udfa4",
        "\ud83d\udc69\u200d\ud83c\udfeb",
        "\ud83d\udc68\u200d\ud83c\udfeb",
        "\ud83d\udc69\u200d\ud83c\udfed",
        "\ud83d\udc68\u200d\ud83c\udfed",
        "\ud83d\udc69\u200d\ud83d\udcbb",
        "\ud83d\udc68\u200d\ud83d\udcbb",
        "\ud83d\udc69\u200d\ud83d\udcbc",
        "\ud83d\udc68\u200d\ud83d\udcbc",
        "\ud83d\udc69\u200d\ud83d\udd27",
        "\ud83d\udc68\u200d\ud83d\udd27",
        "\ud83d\udc69\u200d\ud83d\udd2c",
        "\ud83d\udc68\u200d\ud83d\udd2c",
        "\ud83d\udc69\u200d\ud83c\udfa8",
        "\ud83d\udc68\u200d\ud83c\udfa8",
        "\ud83d\udc69\u200d\ud83d\ude92",
        "\ud83d\udc68\u200d\ud83d\ude92",
        "\ud83d\udc69\u200d\u2708",
        "\ud83d\udc68\u200d\u2708",
        "\ud83d\udc69\u200d\ud83d\ude80",
        "\ud83d\udc68\u200d\ud83d\ude80",
        "\ud83d\udc69\u200d\u2696",
        "\ud83d\udc68\u200d\u2696",
        "\ud83e\udd36",
        "\ud83c\udf85",
        "\ud83d\udc78",
        "\ud83e\udd34",
        "\ud83d\udc70",
        "\ud83e\udd35",
        "\ud83d\udc7c",
        "\ud83e\udd30",
        "\ud83d\ude47\u200d\u2640",
        "\ud83d\ude47",
        "\ud83d\udc81",
        "\ud83d\udc81\u200d\u2642",
        "\ud83d\ude45",
        "\ud83d\ude45\u200d\u2642",
        "\ud83d\ude46",
        "\ud83d\ude46\u200d\u2642",
        "\ud83d\ude4b",
        "\ud83d\ude4b\u200d\u2642",
        "\ud83e\udd26\u200d\u2640",
        "\ud83e\udd26\u200d\u2642",
        "\ud83e\udd37\u200d\u2640",
        "\ud83e\udd37\u200d\u2642",
        "\ud83d\ude4e",
        "\ud83d\ude4e\u200d\u2642",
        "\ud83d\ude4d",
        "\ud83d\ude4d\u200d\u2642",
        "\ud83d\udc87",
        "\ud83d\udc87\u200d\u2642",
        "\ud83d\udc86",
        "\ud83d\udc86\u200d\u2642",
        "\ud83d\udd74",
        "\ud83d\udc83",
        "\ud83d\udd7a",
        "\ud83d\udc6f",
        "\ud83d\udc6f\u200d\u2642",
        "\ud83d\udeb6\u200d\u2640",
        "\ud83d\udeb6",
        "\ud83c\udfc3\u200d\u2640",
        "\ud83c\udfc3",
        "\ud83d\udc6b",
        "\ud83d\udc6d",
        "\ud83d\udc6c",
        "\ud83d\udc91"
//        "\\\\ud83d\\\\udc69\\\\u200d\\\\u2764\\\\ufe0f\\\\u200d\\\\ud83d\\\\udc69\n" +
//        "\\\\ud83d\\\\udc68\\\\u200d\\\\u2764\\\\ufe0f\\\\u200d\\\\ud83d\\\\udc68\n" +
//        "\\\\ud83d\\\\udc8f\n" +
//        "\\\\ud83d\\\\udc69\\\\u200d\\\\u2764\\\\ufe0f\\\\u200d\\\\ud83d\\\\udc8b\\\\u200d\\\\ud83d\\\\udc69\n" +
//        "\\\\ud83d\\\\udc68\\\\u200d\\\\u2764\\\\ufe0f\\\\u200d\\\\ud83d\\\\udc8b\\\\u200d\\\\ud83d\\\\udc68\n" +
//        "\\\\ud83d\\\\udc6a\n" +
//        "\\\\ud83d\\\\udc68\\\\u200d\\\\ud83d\\\\udc69\\\\u200d\\\\ud83d\\\\udc67\n" +
//        "\\\\ud83d\\\\udc68\\\\u200d\\\\ud83d\\\\udc69\\\\u200d\\\\ud83d\\\\udc67\\\\u200d\\\\ud83d\\\\udc66\n" +
//        "\\\\ud83d\\\\udc68\\\\u200d\\\\ud83d\\\\udc69\\\\u200d\\\\ud83d\\\\udc66\\\\u200d\\\\ud83d\\\\udc66\n" +
//        "\\\\ud83d\\\\udc68\\\\u200d\\\\ud83d\\\\udc69\\\\u200d\\\\ud83d\\\\udc67\\\\u200d\\\\ud83d\\\\udc67\n" +
//        "\\\\ud83d\\\\udc69\\\\u200d\\\\ud83d\\\\udc69\\\\u200d\\\\ud83d\\\\udc66\n" +
//        "\\\\ud83d\\\\udc69\\\\u200d\\\\ud83d\\\\udc69\\\\u200d\\\\ud83d\\\\udc67\n" +
//        "\\\\ud83d\\\\udc69\\\\u200d\\\\ud83d\\\\udc69\\\\u200d\\\\ud83d\\\\udc67\\\\u200d\\\\ud83d\\\\udc66\n" +
//        "\\\\ud83d\\\\udc69\\\\u200d\\\\ud83d\\\\udc69\\\\u200d\\\\ud83d\\\\udc66\\\\u200d\\\\ud83d\\\\udc66\n" +
//        "\\\\ud83d\\\\udc69\\\\u200d\\\\ud83d\\\\udc69\\\\u200d\\\\ud83d\\\\udc67\\\\u200d\\\\ud83d\\\\udc67\n" +
//        "\\\\ud83d\\\\udc68\\\\u200d\\\\ud83d\\\\udc68\\\\u200d\\\\ud83d\\\\udc66\n" +
//        "\\\\ud83d\\\\udc68\\\\u200d\\\\ud83d\\\\udc68\\\\u200d\\\\ud83d\\\\udc67\n" +
//        "\\\\ud83d\\\\udc68\\\\u200d\\\\ud83d\\\\udc68\\\\u200d\\\\ud83d\\\\udc67\\\\u200d\\\\ud83d\\\\udc66\n" +
//        "\\\\ud83d\\\\udc68\\\\u200d\\\\ud83d\\\\udc68\\\\u200d\\\\ud83d\\\\udc66\\\\u200d\\\\ud83d\\\\udc66\n" +
//        "\\\\ud83d\\\\udc68\\\\u200d\\\\ud83d\\\\udc68\\\\u200d\\\\ud83d\\\\udc67\\\\u200d\\\\ud83d\\\\udc67\n" +
//        "\\\\ud83d\\\\udc69\\\\u200d\\\\ud83d\\\\udc66\n" +
//        "\\\\ud83d\\\\udc69\\\\u200d\\\\ud83d\\\\udc67\n" +
//        "\\\\ud83d\\\\udc69\\\\u200d\\\\ud83d\\\\udc67\\\\u200d\\\\ud83d\\\\udc66\n" +
//        "\\\\ud83d\\\\udc69\\\\u200d\\\\ud83d\\\\udc66\\\\u200d\\\\ud83d\\\\udc66\n" +
//        "\\\\ud83d\\\\udc69\\\\u200d\\\\ud83d\\\\udc67\\\\u200d\\\\ud83d\\\\udc67\n" +
//        "\\\\ud83d\\\\udc68\\\\u200d\\\\ud83d\\\\udc66\n" +
//        "\\\\ud83d\\\\udc68\\\\u200d\\\\ud83d\\\\udc67\n" +
//        "\\\\ud83d\\\\udc68\\\\u200d\\\\ud83d\\\\udc67\\\\u200d\\\\ud83d\\\\udc66\n" +
//        "\\\\ud83d\\\\udc68\\\\u200d\\\\ud83d\\\\udc66\\\\u200d\\\\ud83d\\\\udc66\n" +
//        "\\\\ud83d\\\\udc68\\\\u200d\\\\ud83d\\\\udc67\\\\u200d\\\\ud83d\\\\udc67\n" +
//                "\\\\ud83d\\\\udc5a\n" +
//                "\\\\ud83d\\\\udc55\n" +
//                "\\\\ud83d\\\\udc56\n" +
//                "\\\\ud83d\\\\udc54\n" +
//                "\\\\ud83d\\\\udc57\n" +
//                "\\\\ud83d\\\\udc59\n" +
//                "\\\\ud83d\\\\udc58\n" +
//                "\\\\ud83d\\\\udc60\n" +
//                "\\\\ud83d\\\\udc61\n" +
//                "\\\\ud83d\\\\udc62\n" +
//                "\\\\ud83d\\\\udc5e\n" +
//                "\\\\ud83d\\\\udc5f\n" +
//                "\\\\ud83d\\\\udc52\n" +
//                "\\\\ud83c\\\\udfa9\n" +
//                "\\\\ud83c\\\\udf93\n" +
//                "\\\\ud83d\\\\udc51\n" +
//                "\\\\u26d1\n" +
//                "\\\\ud83c\\\\udf92\n" +
//                "\\\\ud83d\\\\udc5d\n" +
//                "\\\\ud83d\\\\udc5b\n" +
//                "\\\\ud83d\\\\udc5c\n" +
//                "\\\\ud83d\\\\udcbc\n" +
//                "\\\\ud83d\\\\udc53\n" +
//                "\\\\ud83d\\\\udd76\n" +
//                "\\\\ud83c\\\\udf02\n" +
//                "\\\\u2602\\\\ufe0f\n" +
//                "\\\\ud83d\\\\udc36\n" +
//                "\\\\ud83d\\\\udc31\n" +
//                "\\\\ud83d\\\\udc2d\n" +
//                "\\\\ud83d\\\\udc39\n" +
//                "\\\\ud83d\\\\udc30\n" +
//                "\\\\ud83e\\\\udd8a\n" +
//                "\\\\ud83d\\\\udc3b\n" +
//                "\\\\ud83d\\\\udc3c\n" +
//                "\\\\ud83d\\\\udc28\n" +
//                "\\\\ud83d\\\\udc2f\n" +
//                "\\\\ud83e\\\\udd81\n" +
//                "\\\\ud83d\\\\udc2e\n" +
//                "\\\\ud83d\\\\udc37\n" +
//                "\\\\ud83d\\\\udc3d\n" +
//                "\\\\ud83d\\\\udc38\n" +
//                "\\\\ud83d\\\\udc35\n" +
//                "\\\\ud83d\\\\ude48\n" +
//                "\\\\ud83d\\\\ude49\n" +
//                "\\\\ud83d\\\\ude4a\n" +
//                "\\\\ud83d\\\\udc12\n" +
//                "\\\\ud83d\\\\udc14\n" +
//                "\\\\ud83d\\\\udc27\n" +
//                "\\\\ud83d\\\\udc26\n" +
//                "\\\\ud83d\\\\udc24\n" +
//                "\\\\ud83d\\\\udc23\n" +
//                "\\\\ud83d\\\\udc25\n" +
//                "\\\\ud83e\\\\udd86\n" +
//                "\\\\ud83e\\\\udd85\n" +
//                "\\\\ud83e\\\\udd89\n" +
//                "\\\\ud83e\\\\udd87\n" +
//                "\\\\ud83d\\\\udc3a\n" +
//                "\\\\ud83d\\\\udc17\n" +
//                "\\\\ud83d\\\\udc34\n" +
//                "\\\\ud83e\\\\udd84\n" +
//                "\\\\ud83d\\\\udc1d\n" +
//                "\\\\ud83d\\\\udc1b\n" +
//                "\\\\ud83e\\\\udd8b\n" +
//                "\\\\ud83d\\\\udc0c\n" +
//                "\\\\ud83d\\\\udc1a\n" +
//                "\\\\ud83d\\\\udc1e\n" +
//                "\\\\ud83d\\\\udc1c\n" +
//                "\\\\ud83d\\\\udd77\n" +
//                "\\\\ud83d\\\\udd78\n" +
//                "\\\\ud83d\\\\udc22\n" +
//                "\\\\ud83d\\\\udc0d\n" +
//                "\\\\ud83e\\\\udd8e\n" +
//                "\\\\ud83e\\\\udd82\n" +
//                "\\\\ud83e\\\\udd80\n" +
//                "\\\\ud83e\\\\udd91\n" +
//                "\\\\ud83d\\\\udc19\n" +
//                "\\\\ud83e\\\\udd90\n" +
//                "\\\\ud83d\\\\udc20\n" +
//                "\\\\ud83d\\\\udc1f\n" +
//                "\\\\ud83d\\\\udc21\n" +
//                "\\\\ud83d\\\\udc2c\n" +
//                "\\\\ud83e\\\\udd88\n" +
//                "\\\\ud83d\\\\udc33\n" +
//                "\\\\ud83d\\\\udc0b\n" +
//                "\\\\ud83d\\\\udc0a\n" +
//                "\\\\ud83d\\\\udc06\n" +
//                "\\\\ud83d\\\\udc05\n" +
//                "\\\\ud83d\\\\udc03\n" +
//                "\\\\ud83d\\\\udc02\n" +
//                "\\\\ud83d\\\\udc04\n" +
//                "\\\\ud83e\\\\udd8c\n" +
//                "\\\\ud83d\\\\udc2a\n" +
//                "\\\\ud83d\\\\udc2b\n" +
//                "\\\\ud83d\\\\udc18\n" +
//                "\\\\ud83e\\\\udd8f\n" +
//                "\\\\ud83e\\\\udd8d\n" +
//                "\\\\ud83d\\\\udc0e\n" +
//                "\\\\ud83d\\\\udc16\n" +
//                "\\\\ud83d\\\\udc10\n" +
//                "\\\\ud83d\\\\udc0f\n" +
//                "\\\\ud83d\\\\udc11\n" +
//                "\\\\ud83d\\\\udc15\n" +
//                "\\\\ud83d\\\\udc29\n" +
//                "\\\\ud83d\\\\udc08\n" +
//                "\\\\ud83d\\\\udc13\n" +
//                "\\\\ud83e\\\\udd83\n" +
//                "\\\\ud83d\\\\udd4a\n" +
//                "\\\\ud83d\\\\udc07\n" +
//                "\\\\ud83d\\\\udc01\n" +
//                "\\\\ud83d\\\\udc00\n" +
//                "\\\\ud83d\\\\udc3f\n" +
//                "\\\\ud83d\\\\udc3e\n" +
//                "\\\\ud83d\\\\udc09\n" +
//                "\\\\ud83d\\\\udc32\n" +
//                "\\\\ud83c\\\\udf35\n" +
//                "\\\\ud83c\\\\udf84\n" +
//                "\\\\ud83c\\\\udf32\n" +
//                "\\\\ud83c\\\\udf33\n" +
//                "\\\\ud83c\\\\udf34\n" +
//                "\\\\ud83c\\\\udf31\n" +
//                "\\\\ud83c\\\\udf3f\n" +
//                "\\\\u2618\\\\ufe0f\n" +
//                "\\\\ud83c\\\\udf40\n" +
//                "\\\\ud83c\\\\udf8d\n" +
//                "\\\\ud83c\\\\udf8b\n" +
//                "\\\\ud83c\\\\udf43\n" +
//                "\\\\ud83c\\\\udf42\n" +
//                "\\\\ud83c\\\\udf41\n" +
//                "\\\\ud83c\\\\udf44\n" +
//                "\\\\ud83c\\\\udf3e\n" +
//                "\\\\ud83d\\\\udc90\n" +
//                "\\\\ud83c\\\\udf37\n" +
//                "\\\\ud83c\\\\udf39\n" +
//                "\\\\ud83e\\\\udd40\n" +
//                "\\\\ud83c\\\\udf3b\n" +
//                "\\\\ud83c\\\\udf3c\n" +
//                "\\\\ud83c\\\\udf38\n" +
//                "\\\\ud83c\\\\udf3a\n" +
//                "\\\\ud83c\\\\udf0e\n" +
//                "\\\\ud83c\\\\udf0d\n" +
//                "\\\\ud83c\\\\udf0f\n" +
//                "\\\\ud83c\\\\udf15\n" +
//                "\\\\ud83c\\\\udf16\n" +
//                "\\\\ud83c\\\\udf17\n" +
//                "\\\\ud83c\\\\udf18\n" +
//                "\\\\ud83c\\\\udf11\n" +
//                "\\\\ud83c\\\\udf12\n" +
//                "\\\\ud83c\\\\udf13\n" +
//                "\\\\ud83c\\\\udf14\n" +
//                "\\\\ud83c\\\\udf1a\n" +
//                "\\\\ud83c\\\\udf1d\n" +
//                "\\\\ud83c\\\\udf1e\n" +
//                "\\\\ud83c\\\\udf1b\n" +
//                "\\\\ud83c\\\\udf1c\n" +
//                "\\\\ud83c\\\\udf19\n" +
//                "\\\\ud83d\\\\udcab\n" +
//                "\\\\u2b50\\\\ufe0f\n" +
//                "\\\\ud83c\\\\udf1f\n" +
//                "\\\\u2728\n" +
//                "\\\\u26a1\\\\ufe0f\n" +
//                "\\\\ud83d\\\\udd25\n" +
//                "\\\\ud83d\\\\udca5\n" +
//                "\\\\u2604\n" +
//                "\\\\u2600\\\\ufe0f\n" +
//                "\\\\ud83c\\\\udf24\n" +
//                "\\\\u26c5\\\\ufe0f\n" +
//                "\\\\ud83c\\\\udf25\n" +
//                "\\\\ud83c\\\\udf26\n" +
//                "\\\\ud83c\\\\udf08\n" +
//                "\\\\u2601\\\\ufe0f\n" +
//                "\\\\ud83c\\\\udf27\n" +
//                "\\\\u26c8\n" +
//                "\\\\ud83c\\\\udf29\n" +
//                "\\\\ud83c\\\\udf28\n" +
//                "\\\\u2603\\\\ufe0f\n" +
//                "\\\\u26c4\\\\ufe0f\n" +
//                "\\\\u2744\\\\ufe0f\n" +
//                "\\\\ud83c\\\\udf2c\n" +
//                "\\\\ud83d\\\\udca8\n" +
//                "\\\\ud83c\\\\udf2a\n" +
//                "\\\\ud83c\\\\udf2b\n" +
//                "\\\\ud83c\\\\udf0a\n" +
//                "\\\\ud83d\\\\udca7\n" +
//                "\\\\ud83d\\\\udca6\n" +
//                "\\\\u2614\\\\ufe0f\n" +
//                "\\\\ud83c\\\\udf4f\n" +
//                "\\\\ud83c\\\\udf4e\n" +
//                "\\\\ud83c\\\\udf50\n" +
//                "\\\\ud83c\\\\udf4a\n" +
//                "\\\\ud83c\\\\udf4b\n" +
//                "\\\\ud83c\\\\udf4c\n" +
//                "\\\\ud83c\\\\udf49\n" +
//                "\\\\ud83c\\\\udf47\n" +
//                "\\\\ud83c\\\\udf53\n" +
//                "\\\\ud83c\\\\udf48\n" +
//                "\\\\ud83c\\\\udf52\n" +
//                "\\\\ud83c\\\\udf51\n" +
//                "\\\\ud83c\\\\udf4d\n" +
//                "\\\\ud83e\\\\udd5d\n" +
//                "\\\\ud83e\\\\udd51\n" +
//                "\\\\ud83c\\\\udf45\n" +
//                "\\\\ud83c\\\\udf46\n" +
//                "\\\\ud83e\\\\udd52\n" +
//                "\\\\ud83e\\\\udd55\n" +
//                "\\\\ud83c\\\\udf3d\n" +
//                "\\\\ud83c\\\\udf36\n" +
//                "\\\\ud83e\\\\udd54\n" +
//                "\\\\ud83c\\\\udf60\n" +
//                "\\\\ud83c\\\\udf30\n" +
//                "\\\\ud83e\\\\udd5c\n" +
//                "\\\\ud83c\\\\udf6f\n" +
//                "\\\\ud83e\\\\udd50\n" +
//                "\\\\ud83c\\\\udf5e\n" +
//                "\\\\ud83e\\\\udd56\n" +
//                "\\\\ud83e\\\\uddc0\n" +
//                "\\\\ud83e\\\\udd5a\n" +
//                "\\\\ud83c\\\\udf73\n" +
//                "\\\\ud83e\\\\udd53\n" +
//                "\\\\ud83e\\\\udd5e\n" +
//                "\\\\ud83c\\\\udf64\n" +
//                "\\\\ud83c\\\\udf57\n" +
//                "\\\\ud83c\\\\udf56\n" +
//                "\\\\ud83c\\\\udf55\n" +
//                "\\\\ud83c\\\\udf2d\n" +
//                "\\\\ud83c\\\\udf54\n" +
//                "\\\\ud83c\\\\udf5f\n" +
//                "\\\\ud83e\\\\udd59\n" +
//                "\\\\ud83c\\\\udf2e\n" +
//                "\\\\ud83c\\\\udf2f\n" +
//                "\\\\ud83e\\\\udd57\n" +
//                "\\\\ud83e\\\\udd58\n" +
//                "\\\\ud83c\\\\udf5d\n" +
//                "\\\\ud83c\\\\udf5c\n" +
//                "\\\\ud83c\\\\udf72\n" +
//                "\\\\ud83c\\\\udf65\n" +
//                "\\\\ud83c\\\\udf63\n" +
//                "\\\\ud83c\\\\udf71\n" +
//                "\\\\ud83c\\\\udf5b\n" +
//                "\\\\ud83c\\\\udf5a\n" +
//                "\\\\ud83c\\\\udf59\n" +
//                "\\\\ud83c\\\\udf58\n" +
//                "\\\\ud83c\\\\udf62\n" +
//                "\\\\ud83c\\\\udf61\n" +
//                "\\\\ud83c\\\\udf67\n" +
//                "\\\\ud83c\\\\udf68\n" +
//                "\\\\ud83c\\\\udf66\n" +
//                "\\\\ud83c\\\\udf70\n" +
//                "\\\\ud83c\\\\udf82\n" +
//                "\\\\ud83c\\\\udf6e\n" +
//                "\\\\ud83c\\\\udf6d\n" +
//                "\\\\ud83c\\\\udf6c\n" +
//                "\\\\ud83c\\\\udf6b\n" +
//                "\\\\ud83c\\\\udf7f\n" +
//                "\\\\ud83c\\\\udf69\n" +
//                "\\\\ud83c\\\\udf6a\n" +
//                "\\\\ud83e\\\\udd5b\n" +
//                "\\\\ud83c\\\\udf7c\n" +
//                "\\\\u2615\\\\ufe0f\n" +
//                "\\\\ud83c\\\\udf75\n" +
//                "\\\\ud83c\\\\udf76\n" +
//                "\\\\ud83c\\\\udf7a\n" +
//                "\\\\ud83c\\\\udf7b\n" +
//                "\\\\ud83e\\\\udd42\n" +
//                "\\\\ud83c\\\\udf77\n" +
//                "\\\\ud83e\\\\udd43\n" +
//                "\\\\ud83c\\\\udf78\n" +
//                "\\\\ud83c\\\\udf79\n" +
//                "\\\\ud83c\\\\udf7e\n" +
//                "\\\\ud83e\\\\udd44\n" +
//                "\\\\ud83c\\\\udf74\n" +
//                "\\\\ud83c\\\\udf7d\n" +
//                "\\\\u26bd\\\\ufe0f\n" +
//                "\\\\ud83c\\\\udfc0\n" +
//                "\\\\ud83c\\\\udfc8\n" +
//                "\\\\u26be\\\\ufe0f\n" +
//                "\\\\ud83c\\\\udfbe\n" +
//                "\\\\ud83c\\\\udfd0\n" +
//                "\\\\ud83c\\\\udfc9\n" +
//                "\\\\ud83c\\\\udfb1\n" +
//                "\\\\ud83c\\\\udfd3\n" +
//                "\\\\ud83c\\\\udff8\n" +
//                "\\\\ud83e\\\\udd45\n" +
//                "\\\\ud83c\\\\udfd2\n" +
//                "\\\\ud83c\\\\udfd1\n" +
//                "\\\\ud83c\\\\udfcf\n" +
//                "\\\\u26f3\\\\ufe0f\n" +
//                "\\\\ud83c\\\\udff9\n" +
//                "\\\\ud83c\\\\udfa3\n" +
//                "\\\\ud83e\\\\udd4a\n" +
//                "\\\\ud83e\\\\udd4b\n" +
//                "\\\\u26f8\n" +
//                "\\\\ud83c\\\\udfbf\n" +
//                "\\\\u26f7\n" +
//                "\\\\ud83c\\\\udfc2\n" +
//                "\\\\ud83c\\\\udfcb\\\\ufe0f\\\\u200d\\\\u2640\\\\ufe0f\n" +
//                "\\\\ud83c\\\\udfcb\n" +
//                "\\\\ud83e\\\\udd3a\n" +
//                "\\\\ud83e\\\\udd3c\\\\u200d\\\\u2640\n" +
//                "\\\\ud83e\\\\udd3c\\\\u200d\\\\u2642\n" +
//                "\\\\ud83e\\\\udd38\\\\u200d\\\\u2640\n" +
//                "\\\\ud83e\\\\udd38\\\\u200d\\\\u2642\n" +
//                "\\\\u26f9\\\\ufe0f\\\\u200d\\\\u2640\\\\ufe0f\n" +
//                "\\\\u26f9\n" +
//                "\\\\ud83e\\\\udd3e\\\\u200d\\\\u2640\n" +
//                "\\\\ud83e\\\\udd3e\\\\u200d\\\\u2642\n" +
//                "\\\\ud83c\\\\udfcc\\\\ufe0f\\\\u200d\\\\u2640\\\\ufe0f\n" +
//                "\\\\ud83c\\\\udfcc\n" +
//                "\\\\ud83c\\\\udfc4\\\\u200d\\\\u2640\n" +
//                "\\\\ud83c\\\\udfc4\n" +
//                "\\\\ud83c\\\\udfca\\\\u200d\\\\u2640\n" +
//                "\\\\ud83c\\\\udfca\n" +
//                "\\\\ud83e\\\\udd3d\\\\u200d\\\\u2640\n" +
//                "\\\\ud83e\\\\udd3d\\\\u200d\\\\u2642\n" +
//                "\\\\ud83d\\\\udea3\\\\u200d\\\\u2640\n" +
//                "\\\\ud83d\\\\udea3\n" +
//                "\\\\ud83c\\\\udfc7\n" +
//                "\\\\ud83d\\\\udeb4\\\\u200d\\\\u2640\n" +
//                "\\\\ud83d\\\\udeb4\n" +
//                "\\\\ud83d\\\\udeb5\\\\u200d\\\\u2640\n" +
//                "\\\\ud83d\\\\udeb5\n" +
//                "\\\\ud83c\\\\udfbd\n" +
//                "\\\\ud83c\\\\udfc5\n" +
//                "\\\\ud83c\\\\udf96\n" +
//                "\\\\ud83e\\\\udd47\n" +
//                "\\\\ud83e\\\\udd48\n" +
//                "\\\\ud83e\\\\udd49\n" +
//                "\\\\ud83c\\\\udfc6\n" +
//                "\\\\ud83c\\\\udff5\n" +
//                "\\\\ud83c\\\\udf97\n" +
//                "\\\\ud83c\\\\udfab\n" +
//                "\\\\ud83c\\\\udf9f\n" +
//                "\\\\ud83c\\\\udfaa\n" +
//                "\\\\ud83e\\\\udd39\\\\u200d\\\\u2640\n" +
//                "\\\\ud83e\\\\udd39\\\\u200d\\\\u2642\n" +
//                "\\\\ud83c\\\\udfad\n" +
//                "\\\\ud83c\\\\udfa8\n" +
//                "\\\\ud83c\\\\udfac\n" +
//                "\\\\ud83c\\\\udfa4\n" +
//                "\\\\ud83c\\\\udfa7\n" +
//                "\\\\ud83c\\\\udfbc\n" +
//                "\\\\ud83c\\\\udfb9\n" +
//                "\\\\ud83e\\\\udd41\n" +
//                "\\\\ud83c\\\\udfb7\n" +
//                "\\\\ud83c\\\\udfba\n" +
//                "\\\\ud83c\\\\udfb8\n" +
//                "\\\\ud83c\\\\udfbb\n" +
//                "\\\\ud83c\\\\udfb2\n" +
//                "\\\\ud83c\\\\udfaf\n" +
//                "\\\\ud83c\\\\udfb3\n" +
//                "\\\\ud83c\\\\udfae\n" +
//                "\\\\ud83c\\\\udfb0\n" +
//                "\\\\ud83d\\\\ude97\n" +
//                "\\\\ud83d\\\\ude95\n" +
//                "\\\\ud83d\\\\ude99\n" +
//                "\\\\ud83d\\\\ude8c\n" +
//                "\\\\ud83d\\\\ude8e\n" +
//                "\\\\ud83c\\\\udfce\n" +
//                "\\\\ud83d\\\\ude93\n" +
//                "\\\\ud83d\\\\ude91\n" +
//                "\\\\ud83d\\\\ude92\n" +
//                "\\\\ud83d\\\\ude90\n" +
//                "\\\\ud83d\\\\ude9a\n" +
//                "\\\\ud83d\\\\ude9b\n" +
//                "\\\\ud83d\\\\ude9c\n" +
//                "\\\\ud83d\\\\udef4\n" +
//                "\\\\ud83d\\\\udeb2\n" +
//                "\\\\ud83d\\\\udef5\n" +
//                "\\\\ud83c\\\\udfcd\n" +
//                "\\\\ud83d\\\\udea8\n" +
//                "\\\\ud83d\\\\ude94\n" +
//                "\\\\ud83d\\\\ude8d\n" +
//                "\\\\ud83d\\\\ude98\n" +
//                "\\\\ud83d\\\\ude96\n" +
//                "\\\\ud83d\\\\udea1\n" +
//                "\\\\ud83d\\\\udea0\n" +
//                "\\\\ud83d\\\\ude9f\n" +
//                "\\\\ud83d\\\\ude83\n" +
//                "\\\\ud83d\\\\ude8b\n" +
//                "\\\\ud83d\\\\ude9e\n" +
//                "\\\\ud83d\\\\ude9d\n" +
//                "\\\\ud83d\\\\ude84\n" +
//                "\\\\ud83d\\\\ude85\n" +
//                "\\\\ud83d\\\\ude88\n" +
//                "\\\\ud83d\\\\ude82\n" +
//                "\\\\ud83d\\\\ude86\n" +
//                "\\\\ud83d\\\\ude87\n" +
//                "\\\\ud83d\\\\ude8a\n" +
//                "\\\\ud83d\\\\ude89\n" +
//                "\\\\ud83d\\\\ude81\n" +
//                "\\\\ud83d\\\\udee9\n" +
//                "\\\\u2708\\\\ufe0f\n" +
//                "\\\\ud83d\\\\udeeb\n" +
//                "\\\\ud83d\\\\udeec\n" +
//                "\\\\ud83d\\\\ude80\n" +
//                "\\\\ud83d\\\\udef0\n" +
//                "\\\\ud83d\\\\udcba\n" +
//                "\\\\ud83d\\\\udef6\n" +
//                "\\\\u26f5\\\\ufe0f\n" +
//                "\\\\ud83d\\\\udee5\n" +
//                "\\\\ud83d\\\\udea4\n" +
//                "\\\\ud83d\\\\udef3\n" +
//                "\\\\u26f4\n" +
//                "\\\\ud83d\\\\udea2\n" +
//                "\\\\u2693\\\\ufe0f\n" +
//                "\\\\ud83d\\\\udea7\n" +
//                "\\\\u26fd\\\\ufe0f\n" +
//                "\\\\ud83d\\\\ude8f\n" +
//                "\\\\ud83d\\\\udea6\n" +
//                "\\\\ud83d\\\\udea5\n" +
//                "\\\\ud83d\\\\uddfa\n" +
//                "\\\\ud83d\\\\uddff\n" +
//                "\\\\ud83d\\\\uddfd\n" +
//                "\\\\u26f2\\\\ufe0f\n" +
//                "\\\\ud83d\\\\uddfc\n" +
//                "\\\\ud83c\\\\udff0\n" +
//                "\\\\ud83c\\\\udfef\n" +
//                "\\\\ud83c\\\\udfdf\n" +
//                "\\\\ud83c\\\\udfa1\n" +
//                "\\\\ud83c\\\\udfa2\n" +
//                "\\\\ud83c\\\\udfa0\n" +
//                "\\\\u26f1\n" +
//                "\\\\ud83c\\\\udfd6\n" +
//                "\\\\ud83c\\\\udfdd\n" +
//                "\\\\u26f0\n" +
//                "\\\\ud83c\\\\udfd4\n" +
//                "\\\\ud83d\\\\uddfb\n" +
//                "\\\\ud83c\\\\udf0b\n" +
//                "\\\\ud83c\\\\udfdc\n" +
//                "\\\\ud83c\\\\udfd5\n" +
//                "\\\\u26fa\\\\ufe0f\n" +
//                "\\\\ud83d\\\\udee4\n" +
//                "\\\\ud83d\\\\udee3\n" +
//                "\\\\ud83c\\\\udfd7\n" +
//                "\\\\ud83c\\\\udfed\n" +
//                "\\\\ud83c\\\\udfe0\n" +
//                "\\\\ud83c\\\\udfe1\n" +
//                "\\\\ud83c\\\\udfd8\n" +
//                "\\\\ud83c\\\\udfda\n" +
//                "\\\\ud83c\\\\udfe2\n" +
//                "\\\\ud83c\\\\udfec\n" +
//                "\\\\ud83c\\\\udfe3\n" +
//                "\\\\ud83c\\\\udfe4\n" +
//                "\\\\ud83c\\\\udfe5\n" +
//                "\\\\ud83c\\\\udfe6\n" +
//                "\\\\ud83c\\\\udfe8\n" +
//                "\\\\ud83c\\\\udfea\n" +
//                "\\\\ud83c\\\\udfeb\n" +
//                "\\\\ud83c\\\\udfe9\n" +
//                "\\\\ud83d\\\\udc92\n" +
//                "\\\\ud83c\\\\udfdb\n" +
//                "\\\\u26ea\\\\ufe0f\n" +
//                "\\\\ud83d\\\\udd4c\n" +
//                "\\\\ud83d\\\\udd4d\n" +
//                "\\\\ud83d\\\\udd4b\n" +
//                "\\\\u26e9\n" +
//                "\\\\ud83d\\\\uddfe\n" +
//                "\\\\ud83c\\\\udf91\n" +
//                "\\\\ud83c\\\\udfde\n" +
//                "\\\\ud83c\\\\udf05\n" +
//                "\\\\ud83c\\\\udf04\n" +
//                "\\\\ud83c\\\\udf20\n" +
//                "\\\\ud83c\\\\udf87\n" +
//                "\\\\ud83c\\\\udf86\n" +
//                "\\\\ud83c\\\\udf07\n" +
//                "\\\\ud83c\\\\udf06\n" +
//                "\\\\ud83c\\\\udfd9\n" +
//                "\\\\ud83c\\\\udf03\n" +
//                "\\\\ud83c\\\\udf0c\n" +
//                "\\\\ud83c\\\\udf09\n" +
//                "\\\\ud83c\\\\udf01\n" +
//                "\\\\u231a\\\\ufe0f\n" +
//                "\\\\ud83d\\\\udcf1\n" +
//                "\\\\ud83d\\\\udcf2\n" +
//                "\\\\ud83d\\\\udcbb\n" +
//                "\\\\u2328\\\\ufe0f\n" +
//                "\\\\ud83d\\\\udda5\n" +
//                "\\\\ud83d\\\\udda8\n" +
//                "\\\\ud83d\\\\uddb1\n" +
//                "\\\\ud83d\\\\uddb2\n" +
//                "\\\\ud83d\\\\udd79\n" +
//                "\\\\ud83d\\\\udddc\n" +
//                "\\\\ud83d\\\\udcbd\n" +
//                "\\\\ud83d\\\\udcbe\n" +
//                "\\\\ud83d\\\\udcbf\n" +
//                "\\\\ud83d\\\\udcc0\n" +
//                "\\\\ud83d\\\\udcfc\n" +
//                "\\\\ud83d\\\\udcf7\n" +
//                "\\\\ud83d\\\\udcf8\n" +
//                "\\\\ud83d\\\\udcf9\n" +
//                "\\\\ud83c\\\\udfa5\n" +
//                "\\\\ud83d\\\\udcfd\n" +
//                "\\\\ud83c\\\\udf9e\n" +
//                "\\\\ud83d\\\\udcde\n" +
//                "\\\\u260e\\\\ufe0f\n" +
//                "\\\\ud83d\\\\udcdf\n" +
//                "\\\\ud83d\\\\udce0\n" +
//                "\\\\ud83d\\\\udcfa\n" +
//                "\\\\ud83d\\\\udcfb\n" +
//                "\\\\ud83c\\\\udf99\n" +
//                "\\\\ud83c\\\\udf9a\n" +
//                "\\\\ud83c\\\\udf9b\n" +
//                "\\\\u23f1\n" +
//                "\\\\u23f2\n" +
//                "\\\\u23f0\n" +
//                "\\\\ud83d\\\\udd70\n" +
//                "\\\\u231b\\\\ufe0f\n" +
//                "\\\\u23f3\n" +
//                "\\\\ud83d\\\\udce1\n" +
//                "\\\\ud83d\\\\udd0b\n" +
//                "\\\\ud83d\\\\udd0c\n" +
//                "\\\\ud83d\\\\udca1\n" +
//                "\\\\ud83d\\\\udd26\n" +
//                "\\\\ud83d\\\\udd6f\n" +
//                "\\\\ud83d\\\\uddd1\n" +
//                "\\\\ud83d\\\\udee2\n" +
//                "\\\\ud83d\\\\udcb8\n" +
//                "\\\\ud83d\\\\udcb5\n" +
//                "\\\\ud83d\\\\udcb4\n" +
//                "\\\\ud83d\\\\udcb6\n" +
//                "\\\\ud83d\\\\udcb7\n" +
//                "\\\\ud83d\\\\udcb0\n" +
//                "\\\\ud83d\\\\udcb3\n" +
//                "\\\\ud83d\\\\udc8e\n" +
//                "\\\\u2696\\\\ufe0f\n" +
//                "\\\\ud83d\\\\udd27\n" +
//                "\\\\ud83d\\\\udd28\n" +
//                "\\\\u2692\n" +
//                "\\\\ud83d\\\\udee0\n" +
//                "\\\\u26cf\n" +
//                "\\\\ud83d\\\\udd29\n" +
//                "\\\\u2699\\\\ufe0f\n" +
//                "\\\\u26d3\n" +
//                "\\\\ud83d\\\\udd2b\n" +
//                "\\\\ud83d\\\\udca3\n" +
//                "\\\\ud83d\\\\udd2a\n" +
//                "\\\\ud83d\\\\udde1\n" +
//                "\\\\u2694\\\\ufe0f\n" +
//                "\\\\ud83d\\\\udee1\n" +
//                "\\\\ud83d\\\\udeac\n" +
//                "\\\\u26b0\\\\ufe0f\n" +
//                "\\\\u26b1\\\\ufe0f\n" +
//                "\\\\ud83c\\\\udffa\n" +
//                "\\\\ud83d\\\\udd2e\n" +
//                "\\\\ud83d\\\\udcff\n" +
//                "\\\\ud83d\\\\udc88\n" +
//                "\\\\u2697\\\\ufe0f\n" +
//                "\\\\ud83d\\\\udd2d\n" +
//                "\\\\ud83d\\\\udd2c\n" +
//                "\\\\ud83d\\\\udd73\n" +
//                "\\\\ud83d\\\\udc8a\n" +
//                "\\\\ud83d\\\\udc89\n" +
//                "\\\\ud83c\\\\udf21\n" +
//                "\\\\ud83d\\\\udebd\n" +
//                "\\\\ud83d\\\\udeb0\n" +
//                "\\\\ud83d\\\\udebf\n" +
//                "\\\\ud83d\\\\udec1\n" +
//                "\\\\ud83d\\\\udec0\n" +
//                "\\\\ud83d\\\\udece\n" +
//                "\\\\ud83d\\\\udd11\n" +
//                "\\\\ud83d\\\\udddd\n" +
//                "\\\\ud83d\\\\udeaa\n" +
//                "\\\\ud83d\\\\udecb\n" +
//                "\\\\ud83d\\\\udecf\n" +
//                "\\\\ud83d\\\\udecc\n" +
//                "\\\\ud83d\\\\uddbc\n" +
//                "\\\\ud83d\\\\udecd\n" +
//                "\\\\ud83d\\\\uded2\n" +
//                "\\\\ud83c\\\\udf81\n" +
//                "\\\\ud83c\\\\udf88\n" +
//                "\\\\ud83c\\\\udf8f\n" +
//                "\\\\ud83c\\\\udf80\n" +
//                "\\\\ud83c\\\\udf8a\n" +
//                "\\\\ud83c\\\\udf89\n" +
//                "\\\\ud83c\\\\udf8e\n" +
//                "\\\\ud83c\\\\udfee\n" +
//                "\\\\ud83c\\\\udf90\n" +
//                "\\\\u2709\\\\ufe0f\n" +
//                "\\\\ud83d\\\\udce9\n" +
//                "\\\\ud83d\\\\udce8\n" +
//                "\\\\ud83d\\\\udce7\n" +
//                "\\\\ud83d\\\\udc8c\n" +
//                "\\\\ud83d\\\\udce5\n" +
//                "\\\\ud83d\\\\udce4\n" +
//                "\\\\ud83d\\\\udce6\n" +
//                "\\\\ud83c\\\\udff7\n" +
//                "\\\\ud83d\\\\udcea\n" +
//                "\\\\ud83d\\\\udceb\n" +
//                "\\\\ud83d\\\\udcec\n" +
//                "\\\\ud83d\\\\udced\n" +
//                "\\\\ud83d\\\\udcee\n" +
//                "\\\\ud83d\\\\udcef\n" +
//                "\\\\ud83d\\\\udcdc\n" +
//                "\\\\ud83d\\\\udcc3\n" +
//                "\\\\ud83d\\\\udcc4\n" +
//                "\\\\ud83d\\\\udcd1\n" +
//                "\\\\ud83d\\\\udcca\n" +
//                "\\\\ud83d\\\\udcc8\n" +
//                "\\\\ud83d\\\\udcc9\n" +
//                "\\\\ud83d\\\\uddd2\n" +
//                "\\\\ud83d\\\\uddd3\n" +
//                "\\\\ud83d\\\\udcc6\n" +
//                "\\\\ud83d\\\\udcc5\n" +
//                "\\\\ud83d\\\\udcc7\n" +
//                "\\\\ud83d\\\\uddc3\n" +
//                "\\\\ud83d\\\\uddf3\n" +
//                "\\\\ud83d\\\\uddc4\n" +
//                "\\\\ud83d\\\\udccb\n" +
//                "\\\\ud83d\\\\udcc1\n" +
//                "\\\\ud83d\\\\udcc2\n" +
//                "\\\\ud83d\\\\uddc2\n" +
//                "\\\\ud83d\\\\uddde\n" +
//                "\\\\ud83d\\\\udcf0\n" +
//                "\\\\ud83d\\\\udcd3\n" +
//                "\\\\ud83d\\\\udcd4\n" +
//                "\\\\ud83d\\\\udcd2\n" +
//                "\\\\ud83d\\\\udcd5\n" +
//                "\\\\ud83d\\\\udcd7\n" +
//                "\\\\ud83d\\\\udcd8\n" +
//                "\\\\ud83d\\\\udcd9\n" +
//                "\\\\ud83d\\\\udcda\n" +
//                "\\\\ud83d\\\\udcd6\n" +
//                "\\\\ud83d\\\\udd16\n" +
//                "\\\\ud83d\\\\udd17\n" +
//                "\\\\ud83d\\\\udcce\n" +
//                "\\\\ud83d\\\\udd87\n" +
//                "\\\\ud83d\\\\udcd0\n" +
//                "\\\\ud83d\\\\udccf\n" +
//                "\\\\ud83d\\\\udccc\n" +
//                "\\\\ud83d\\\\udccd\n" +
//                "\\\\u2702\\\\ufe0f\n" +
//                "\\\\ud83d\\\\udd8a\n" +
//                "\\\\ud83d\\\\udd8b\n" +
//                "\\\\u2712\\\\ufe0f\n" +
//                "\\\\ud83d\\\\udd8c\n" +
//                "\\\\ud83d\\\\udd8d\n" +
//                "\\\\ud83d\\\\udcdd\n" +
//                "\\\\u270f\\\\ufe0f\n" +
//                "\\\\ud83d\\\\udd0d\n" +
//                "\\\\ud83d\\\\udd0e\n" +
//                "\\\\ud83d\\\\udd0f\n" +
//                "\\\\ud83d\\\\udd10\n" +
//                "\\\\ud83d\\\\udd12\n" +
//                "\\\\ud83d\\\\udd13\n" +
//                "\\\\u2764\\\\ufe0f\n" +
//                "\\\\ud83d\\\\udc9b\n" +
//                "\\\\ud83d\\\\udc9a\n" +
//                "\\\\ud83d\\\\udc99\n" +
//                "\\\\ud83d\\\\udc9c\n" +
//                "\\\\ud83d\\\\udda4\n" +
//                "\\\\ud83d\\\\udc94\n" +
//                "\\\\u2763\\\\ufe0f\n" +
//                "\\\\ud83d\\\\udc95\n" +
//                "\\\\ud83d\\\\udc9e\n" +
//                "\\\\ud83d\\\\udc93\n" +
//                "\\\\ud83d\\\\udc97\n" +
//                "\\\\ud83d\\\\udc96\n" +
//                "\\\\ud83d\\\\udc98\n" +
//                "\\\\ud83d\\\\udc9d\n" +
//                "\\\\ud83d\\\\udc9f\n" +
//                "\\\\u262e\\\\ufe0f\n" +
//                "\\\\u271d\\\\ufe0f\n" +
//                "\\\\u262a\\\\ufe0f\n" +
//                "\\\\ud83d\\\\udd49\n" +
//                "\\\\u2638\\\\ufe0f\n" +
//                "\\\\u2721\\\\ufe0f\n" +
//                "\\\\ud83d\\\\udd2f\n" +
//                "\\\\ud83d\\\\udd4e\n" +
//                "\\\\u262f\\\\ufe0f\n" +
//                "\\\\u2626\\\\ufe0f\n" +
//                "\\\\ud83d\\\\uded0\n" +
//                "\\\\u26ce\n" +
//                "\\\\u2648\\\\ufe0f\n" +
//                "\\\\u2649\\\\ufe0f\n" +
//                "\\\\u264a\\\\ufe0f\n" +
//                "\\\\u264b\\\\ufe0f\n" +
//                "\\\\u264c\\\\ufe0f\n" +
//                "\\\\u264d\\\\ufe0f\n" +
//                "\\\\u264e\\\\ufe0f\n" +
//                "\\\\u264f\\\\ufe0f\n" +
//                "\\\\u2650\\\\ufe0f\n" +
//                "\\\\u2651\\\\ufe0f\n" +
//                "\\\\u2652\\\\ufe0f\n" +
//                "\\\\u2653\\\\ufe0f\n" +
//                "\\\\ud83c\\\\udd94\n" +
//                "\\\\u269b\\\\ufe0f\n" +
//                "\\\\ud83c\\\\ude51\n" +
//                "\\\\u2622\\\\ufe0f\n" +
//                "\\\\u2623\\\\ufe0f\n" +
//                "\\\\ud83d\\\\udcf4\n" +
//                "\\\\ud83d\\\\udcf3\n" +
//                "\\\\ud83c\\\\ude36\n" +
//                "\\\\ud83c\\\\ude1a\\\\ufe0f\n" +
//                "\\\\ud83c\\\\ude38\n" +
//                "\\\\ud83c\\\\ude3a\n" +
//                "\\\\ud83c\\\\ude37\\\\ufe0f\n" +
//                "\\\\u2734\\\\ufe0f\n" +
//                "\\\\ud83c\\\\udd9a\n" +
//                "\\\\ud83d\\\\udcae\n" +
//                "\\\\ud83c\\\\ude50\n" +
//                "\\\\u3299\\\\ufe0f\n" +
//                "\\\\u3297\\\\ufe0f\n" +
//                "\\\\ud83c\\\\ude34\n" +
//                "\\\\ud83c\\\\ude35\n" +
//                "\\\\ud83c\\\\ude39\n" +
//                "\\\\ud83c\\\\ude32\n" +
//                "\\\\ud83c\\\\udd70\\\\ufe0f\n" +
//                "\\\\ud83c\\\\udd71\\\\ufe0f\n" +
//                "\\\\ud83c\\\\udd8e\n" +
//                "\\\\ud83c\\\\udd91\n" +
//                "\\\\ud83c\\\\udd7e\\\\ufe0f\n" +
//                "\\\\ud83c\\\\udd98\n" +
//                "\\\\u274c\n" +
//                "\\\\u2b55\\\\ufe0f\n" +
//                "\\\\ud83d\\\\uded1\n" +
//                "\\\\u26d4\\\\ufe0f\n" +
//                "\\\\ud83d\\\\udcdb\n" +
//                "\\\\ud83d\\\\udeab\n" +
//                "\\\\ud83d\\\\udcaf\n" +
//                "\\\\ud83d\\\\udca2\n" +
//                "\\\\u2668\\\\ufe0f\n" +
//                "\\\\ud83d\\\\udeb7\n" +
//                "\\\\ud83d\\\\udeaf\n" +
//                "\\\\ud83d\\\\udeb3\n" +
//                "\\\\ud83d\\\\udeb1\n" +
//                "\\\\ud83d\\\\udd1e\n" +
//                "\\\\ud83d\\\\udcf5\n" +
//                "\\\\ud83d\\\\udead\n" +
//                "\\\\u2757\\\\ufe0f\n" +
//                "\\\\u2755\n" +
//                "\\\\u2753\n" +
//                "\\\\u2754\n" +
//                "\\\\u203c\\\\ufe0f\n" +
//                "\\\\u2049\\\\ufe0f\n" +
//                "\\\\ud83d\\\\udd05\n" +
//                "\\\\ud83d\\\\udd06\n" +
//                "\\\\u303d\\\\ufe0f\n" +
//                "\\\\u26a0\\\\ufe0f\n" +
//                "\\\\ud83d\\\\udeb8\n" +
//                "\\\\ud83d\\\\udd31\n" +
//                "\\\\u269c\\\\ufe0f\n" +
//                "\\\\ud83d\\\\udd30\n" +
//                "\\\\u267b\\\\ufe0f\n" +
//                "\\\\u2705\n" +
//                "\\\\ud83c\\\\ude2f\\\\ufe0f\n" +
//                "\\\\ud83d\\\\udcb9\n" +
//                "\\\\u2747\\\\ufe0f\n" +
//                "\\\\u2733\\\\ufe0f\n" +
//                "\\\\u274e\n" +
//                "\\\\ud83c\\\\udf10\n" +
//                "\\\\ud83d\\\\udca0\n" +
//                "\\\\u24c2\\\\ufe0f\n" +
//                "\\\\ud83c\\\\udf00\n" +
//                "\\\\ud83d\\\\udca4\n" +
//                "\\\\ud83c\\\\udfe7\n" +
//                "\\\\ud83d\\\\udebe\n" +
//                "\\\\u267f\\\\ufe0f\n" +
//                "\\\\ud83c\\\\udd7f\\\\ufe0f\n" +
//                "\\\\ud83c\\\\ude33\n" +
//                "\\\\ud83c\\\\ude02\\\\ufe0f\n" +
//                "\\\\ud83d\\\\udec2\n" +
//                "\\\\ud83d\\\\udec3\n" +
//                "\\\\ud83d\\\\udec4\n" +
//                "\\\\ud83d\\\\udec5\n" +
//                "\\\\ud83d\\\\udeb9\n" +
//                "\\\\ud83d\\\\udeba\n" +
//                "\\\\ud83d\\\\udebc\n" +
//                "\\\\ud83d\\\\udebb\n" +
//                "\\\\ud83d\\\\udeae\n" +
//                "\\\\ud83c\\\\udfa6\n" +
//                "\\\\ud83d\\\\udcf6\n" +
//                "\\\\ud83c\\\\ude01\n" +
//                "\\\\ud83d\\\\udd23\n" +
//                "\\\\u2139\\\\ufe0f\n" +
//                "\\\\ud83d\\\\udd24\n" +
//                "\\\\ud83d\\\\udd21\n" +
//                "\\\\ud83d\\\\udd20\n" +
//                "\\\\ud83c\\\\udd96\n" +
//                "\\\\ud83c\\\\udd97\n" +
//                "\\\\ud83c\\\\udd99\n" +
//                "\\\\ud83c\\\\udd92\n" +
//                "\\\\ud83c\\\\udd95\n" +
//                "\\\\ud83c\\\\udd93\n" +
//                "\\\\u30\\\\ufe0f\\\\u20e3\n" +
//                "\\\\u31\\\\ufe0f\\\\u20e3\n" +
//                "\\\\u32\\\\ufe0f\\\\u20e3\n" +
//                "\\\\u33\\\\ufe0f\\\\u20e3\n" +
//                "\\\\u34\\\\ufe0f\\\\u20e3\n" +
//                "\\\\u35\\\\ufe0f\\\\u20e3\n" +
//                "\\\\u36\\\\ufe0f\\\\u20e3\n" +
//                "\\\\u37\\\\ufe0f\\\\u20e3\n" +
//                "\\\\u38\\\\ufe0f\\\\u20e3\n" +
//                "\\\\u39\\\\ufe0f\\\\u20e3\n" +
//                "\\\\ud83d\\\\udd1f\n" +
//                "\\\\ud83d\\\\udd22\n" +
//                "\\\\u23\\\\ufe0f\\\\u20e3\n" +
//                "\\\\u2a\\\\ufe0f\\\\u20e3\n" +
//                "\\\\u25b6\\\\ufe0f\n" +
//                "\\\\u23f8\n" +
//                "\\\\u23ef\n" +
//                "\\\\u23f9\n" +
//                "\\\\u23fa\n" +
//                "\\\\u23ed\n" +
//                "\\\\u23ee\n" +
//                "\\\\u23e9\n" +
//                "\\\\u23ea\n" +
//                "\\\\u23eb\n" +
//                "\\\\u23ec\n" +
//                "\\\\u25c0\\\\ufe0f\n" +
//                "\\\\ud83d\\\\udd3c\n" +
//                "\\\\ud83d\\\\udd3d\n" +
//                "\\\\u27a1\\\\ufe0f\n" +
//                "\\\\u2b05\\\\ufe0f\n" +
//                "\\\\u2b06\\\\ufe0f\n" +
//                "\\\\u2b07\\\\ufe0f\n" +
//                "\\\\u2197\\\\ufe0f\n" +
//                "\\\\u2198\\\\ufe0f\n" +
//                "\\\\u2199\\\\ufe0f\n" +
//                "\\\\u2196\\\\ufe0f\n" +
//                "\\\\u2195\\\\ufe0f\n" +
//                "\\\\u2194\\\\ufe0f\n" +
//                "\\\\u21aa\\\\ufe0f\n" +
//                "\\\\u21a9\\\\ufe0f\n" +
//                "\\\\u2934\\\\ufe0f\n" +
//                "\\\\u2935\\\\ufe0f\n" +
//                "\\\\ud83d\\\\udd00\n" +
//                "\\\\ud83d\\\\udd01\n" +
//                "\\\\ud83d\\\\udd02\n" +
//                "\\\\ud83d\\\\udd04\n" +
//                "\\\\ud83d\\\\udd03\n" +
//                "\\\\ud83c\\\\udfb5\n" +
//                "\\\\ud83c\\\\udfb6\n" +
//                "\\\\u2795\n" +
//                "\\\\u2796\n" +
//                "\\\\u2797\n" +
//                "\\\\u2716\\\\ufe0f\n" +
//                "\\\\ud83d\\\\udcb2\n" +
//                "\\\\ud83d\\\\udcb1\n" +
//                "\\\\u2122\\\\ufe0f\n" +
//                "\\\\ua9\\\\ufe0f\n" +
//                "\\\\uae\\\\ufe0f\n" +
//                "\\\\u3030\\\\ufe0f\n" +
//                "\\\\u27b0\n" +
//                "\\\\u27bf\n" +
//                "\\\\ud83d\\\\udd1a\n" +
//                "\\\\ud83d\\\\udd19\n" +
//                "\\\\ud83d\\\\udd1b\n" +
//                "\\\\ud83d\\\\udd1d\n" +
//                "\\\\ud83d\\\\udd1c\n" +
//                "\\\\u2714\\\\ufe0f\n" +
//                "\\\\u2611\\\\ufe0f\n" +
//                "\\\\ud83d\\\\udd18\n" +
//                "\\\\u26aa\\\\ufe0f\n" +
//                "\\\\u26ab\\\\ufe0f\n" +
//                "\\\\ud83d\\\\udd34\n" +
//                "\\\\ud83d\\\\udd35\n" +
//                "\\\\ud83d\\\\udd3a\n" +
//                "\\\\ud83d\\\\udd3b\n" +
//                "\\\\ud83d\\\\udd38\n" +
//                "\\\\ud83d\\\\udd39\n" +
//                "\\\\ud83d\\\\udd36\n" +
//                "\\\\ud83d\\\\udd37\n" +
//                "\\\\ud83d\\\\udd33\n" +
//                "\\\\ud83d\\\\udd32\n" +
//                "\\\\u25aa\\\\ufe0f\n" +
//                "\\\\u25ab\\\\ufe0f\n" +
//                "\\\\u25fe\\\\ufe0f\n" +
//                "\\\\u25fd\\\\ufe0f\n" +
//                "\\\\u25fc\\\\ufe0f\n" +
//                "\\\\u25fb\\\\ufe0f\n" +
//                "\\\\u2b1b\\\\ufe0f\n" +
//                "\\\\u2b1c\\\\ufe0f\n" +
//                "\\\\ud83d\\\\udd08\n" +
//                "\\\\ud83d\\\\udd07\n" +
//                "\\\\ud83d\\\\udd09\n" +
//                "\\\\ud83d\\\\udd0a\n" +
//                "\\\\ud83d\\\\udd14\n" +
//                "\\\\ud83d\\\\udd15\n" +
//                "\\\\ud83d\\\\udce3\n" +
//                "\\\\ud83d\\\\udce2\n" +
//                "\\\\ud83d\\\\udc41\\\\u200d\\\\ud83d\\\\udde8\n" +
//                "\\\\ud83d\\\\udcac\n" +
//                "\\\\ud83d\\\\udcad\n" +
//                "\\\\ud83d\\\\uddef\n" +
//                "\\\\u2660\\\\ufe0f\n" +
//                "\\\\u2663\\\\ufe0f\n" +
//                "\\\\u2665\\\\ufe0f\n" +
//                "\\\\u2666\\\\ufe0f\n" +
//                "\\\\ud83c\\\\udccf\n" +
//                "\\\\ud83c\\\\udfb4\n" +
//                "\\\\ud83c\\\\udc04\\\\ufe0f\n" +
//                "\\\\ud83d\\\\udd50\n" +
//                "\\\\ud83d\\\\udd51\n" +
//                "\\\\ud83d\\\\udd52\n" +
//                "\\\\ud83d\\\\udd53\n" +
//                "\\\\ud83d\\\\udd54\n" +
//                "\\\\ud83d\\\\udd55\n" +
//                "\\\\ud83d\\\\udd56\n" +
//                "\\\\ud83d\\\\udd57\n" +
//                "\\\\ud83d\\\\udd58\n" +
//                "\\\\ud83d\\\\udd59\n" +
//                "\\\\ud83d\\\\udd5a\n" +
//                "\\\\ud83d\\\\udd5b\n" +
//                "\\\\ud83d\\\\udd5c\n" +
//                "\\\\ud83d\\\\udd5d\n" +
//                "\\\\ud83d\\\\udd5e\n" +
//                "\\\\ud83d\\\\udd5f\n" +
//                "\\\\ud83d\\\\udd60\n" +
//                "\\\\ud83d\\\\udd61\n" +
//                "\\\\ud83d\\\\udd62\n" +
//                "\\\\ud83d\\\\udd63\n" +
//                "\\\\ud83d\\\\udd64\n" +
//                "\\\\ud83d\\\\udd65\n" +
//                "\\\\ud83d\\\\udd66\n" +
//                "\\\\ud83d\\\\udd67\n" +
//                "\\\\ud83c\\\\udff3\\\\ufe0f\n" +
//                "\\\\ud83c\\\\udff4\n" +
//                "\\\\ud83c\\\\udfc1\n" +
//                "\\\\ud83d\\\\udea9\n" +
//                "\\\\ud83c\\\\udff3\\\\ufe0f\\\\u200d\\\\ud83c\\\\udf08\n" +
//                "\\\\ud83c\\\\udde6\\\\ud83c\\\\uddeb\n" +
//                "\\\\ud83c\\\\udde6\\\\ud83c\\\\uddfd\n" +
//                "\\\\ud83c\\\\udde6\\\\ud83c\\\\uddf1\n" +
//                "\\\\ud83c\\\\udde9\\\\ud83c\\\\uddff\n" +
//                "\\\\ud83c\\\\udde6\\\\ud83c\\\\uddf8\n" +
//                "\\\\ud83c\\\\udde6\\\\ud83c\\\\udde9\n" +
//                "\\\\ud83c\\\\udde6\\\\ud83c\\\\uddf4\n" +
//                "\\\\ud83c\\\\udde6\\\\ud83c\\\\uddee\n" +
//                "\\\\ud83c\\\\udde6\\\\ud83c\\\\uddf6\n" +
//                "\\\\ud83c\\\\udde6\\\\ud83c\\\\uddec\n" +
//                "\\\\ud83c\\\\udde6\\\\ud83c\\\\uddf7\n" +
//                "\\\\ud83c\\\\udde6\\\\ud83c\\\\uddf2\n" +
//                "\\\\ud83c\\\\udde6\\\\ud83c\\\\uddfc\n" +
//                "\\\\ud83c\\\\udde6\\\\ud83c\\\\uddfa\n" +
//                "\\\\ud83c\\\\udde6\\\\ud83c\\\\uddf9\n" +
//                "\\\\ud83c\\\\udde6\\\\ud83c\\\\uddff\n" +
//                "\\\\ud83c\\\\udde7\\\\ud83c\\\\uddf8\n" +
//                "\\\\ud83c\\\\udde7\\\\ud83c\\\\udded\n" +
//                "\\\\ud83c\\\\udde7\\\\ud83c\\\\udde9\n" +
//                "\\\\ud83c\\\\udde7\\\\ud83c\\\\udde7\n" +
//                "\\\\ud83c\\\\udde7\\\\ud83c\\\\uddfe\n" +
//                "\\\\ud83c\\\\udde7\\\\ud83c\\\\uddea\n" +
//                "\\\\ud83c\\\\udde7\\\\ud83c\\\\uddff\n" +
//                "\\\\ud83c\\\\udde7\\\\ud83c\\\\uddef\n" +
//                "\\\\ud83c\\\\udde7\\\\ud83c\\\\uddf2\n" +
//                "\\\\ud83c\\\\udde7\\\\ud83c\\\\uddf9\n" +
//                "\\\\ud83c\\\\udde7\\\\ud83c\\\\uddf4\n" +
//                "\\\\ud83c\\\\udde7\\\\ud83c\\\\uddf6\n" +
//                "\\\\ud83c\\\\udde7\\\\ud83c\\\\udde6\n" +
//                "\\\\ud83c\\\\udde7\\\\ud83c\\\\uddfc\n" +
//                "\\\\ud83c\\\\udde7\\\\ud83c\\\\uddf7\n" +
//                "\\\\ud83c\\\\uddee\\\\ud83c\\\\uddf4\n" +
//                "\\\\ud83c\\\\uddfb\\\\ud83c\\\\uddec\n" +
//                "\\\\ud83c\\\\udde7\\\\ud83c\\\\uddf3\n" +
//                "\\\\ud83c\\\\udde7\\\\ud83c\\\\uddec\n" +
//                "\\\\ud83c\\\\udde7\\\\ud83c\\\\uddeb\n" +
//                "\\\\ud83c\\\\udde7\\\\ud83c\\\\uddee\n" +
//                "\\\\ud83c\\\\udde8\\\\ud83c\\\\uddfb\n" +
//                "\\\\ud83c\\\\uddf0\\\\ud83c\\\\udded\n" +
//                "\\\\ud83c\\\\udde8\\\\ud83c\\\\uddf2\n" +
//                "\\\\ud83c\\\\udde8\\\\ud83c\\\\udde6\n" +
//                "\\\\ud83c\\\\uddee\\\\ud83c\\\\udde8\n" +
//                "\\\\ud83c\\\\uddf0\\\\ud83c\\\\uddfe\n" +
//                "\\\\ud83c\\\\udde8\\\\ud83c\\\\uddeb\n" +
//                "\\\\ud83c\\\\uddf9\\\\ud83c\\\\udde9\n" +
//                "\\\\ud83c\\\\udde8\\\\ud83c\\\\uddf1\n" +
//                "\\\\ud83c\\\\udde8\\\\ud83c\\\\uddf3\n" +
//                "\\\\ud83c\\\\udde8\\\\ud83c\\\\uddfd\n" +
//                "\\\\ud83c\\\\udde8\\\\ud83c\\\\udde8\n" +
//                "\\\\ud83c\\\\udde8\\\\ud83c\\\\uddf4\n" +
//                "\\\\ud83c\\\\uddf0\\\\ud83c\\\\uddf2\n" +
//                "\\\\ud83c\\\\udde8\\\\ud83c\\\\uddec\n" +
//                "\\\\ud83c\\\\udde8\\\\ud83c\\\\udde9\n" +
//                "\\\\ud83c\\\\udde8\\\\ud83c\\\\uddf0\n" +
//                "\\\\ud83c\\\\udde8\\\\ud83c\\\\uddf7\n" +
//                "\\\\ud83c\\\\udde8\\\\ud83c\\\\uddee\n" +
//                "\\\\ud83c\\\\udded\\\\ud83c\\\\uddf7\n" +
//                "\\\\ud83c\\\\udde8\\\\ud83c\\\\uddfa\n" +
//                "\\\\ud83c\\\\udde8\\\\ud83c\\\\uddfc\n" +
//                "\\\\ud83c\\\\udde8\\\\ud83c\\\\uddfe\n" +
//                "\\\\ud83c\\\\udde8\\\\ud83c\\\\uddff\n" +
//                "\\\\ud83c\\\\udde9\\\\ud83c\\\\uddf0\n" +
//                "\\\\ud83c\\\\udde9\\\\ud83c\\\\uddef\n" +
//                "\\\\ud83c\\\\udde9\\\\ud83c\\\\uddf2\n" +
//                "\\\\ud83c\\\\udde9\\\\ud83c\\\\uddf4\n" +
//                "\\\\ud83c\\\\uddea\\\\ud83c\\\\udde8\n" +
//                "\\\\ud83c\\\\uddea\\\\ud83c\\\\uddec\n" +
//                "\\\\ud83c\\\\uddf8\\\\ud83c\\\\uddfb\n" +
//                "\\\\ud83c\\\\uddec\\\\ud83c\\\\uddf6\n" +
//                "\\\\ud83c\\\\uddea\\\\ud83c\\\\uddf7\n" +
//                "\\\\ud83c\\\\uddea\\\\ud83c\\\\uddea\n" +
//                "\\\\ud83c\\\\uddea\\\\ud83c\\\\uddf9\n" +
//                "\\\\ud83c\\\\uddea\\\\ud83c\\\\uddfa\n" +
//                "\\\\ud83c\\\\uddeb\\\\ud83c\\\\uddf0\n" +
//                "\\\\ud83c\\\\uddeb\\\\ud83c\\\\uddf4\n" +
//                "\\\\ud83c\\\\uddeb\\\\ud83c\\\\uddef\n" +
//                "\\\\ud83c\\\\uddeb\\\\ud83c\\\\uddee\n" +
//                "\\\\ud83c\\\\uddeb\\\\ud83c\\\\uddf7\n" +
//                "\\\\ud83c\\\\uddec\\\\ud83c\\\\uddeb\n" +
//                "\\\\ud83c\\\\uddf5\\\\ud83c\\\\uddeb\n" +
//                "\\\\ud83c\\\\uddf9\\\\ud83c\\\\uddeb\n" +
//                "\\\\ud83c\\\\uddec\\\\ud83c\\\\udde6\n" +
//                "\\\\ud83c\\\\uddec\\\\ud83c\\\\uddf2\n" +
//                "\\\\ud83c\\\\uddec\\\\ud83c\\\\uddea\n" +
//                "\\\\ud83c\\\\udde9\\\\ud83c\\\\uddea\n" +
//                "\\\\ud83c\\\\uddec\\\\ud83c\\\\udded\n" +
//                "\\\\ud83c\\\\uddec\\\\ud83c\\\\uddee\n" +
//                "\\\\ud83c\\\\uddec\\\\ud83c\\\\uddf7\n" +
//                "\\\\ud83c\\\\uddec\\\\ud83c\\\\uddf1\n" +
//                "\\\\ud83c\\\\uddec\\\\ud83c\\\\udde9\n" +
//                "\\\\ud83c\\\\uddec\\\\ud83c\\\\uddf5\n" +
//                "\\\\ud83c\\\\uddec\\\\ud83c\\\\uddfa\n" +
//                "\\\\ud83c\\\\uddec\\\\ud83c\\\\uddf9\n" +
//                "\\\\ud83c\\\\uddec\\\\ud83c\\\\uddec\n" +
//                "\\\\ud83c\\\\uddec\\\\ud83c\\\\uddf3\n" +
//                "\\\\ud83c\\\\uddec\\\\ud83c\\\\uddfc\n" +
//                "\\\\ud83c\\\\uddec\\\\ud83c\\\\uddfe\n" +
//                "\\\\ud83c\\\\udded\\\\ud83c\\\\uddf9\n" +
//                "\\\\ud83c\\\\udded\\\\ud83c\\\\uddf3\n" +
//                "\\\\ud83c\\\\udded\\\\ud83c\\\\uddf0\n" +
//                "\\\\ud83c\\\\udded\\\\ud83c\\\\uddfa\n" +
//                "\\\\ud83c\\\\uddee\\\\ud83c\\\\uddf8\n" +
//                "\\\\ud83c\\\\uddee\\\\ud83c\\\\uddf3\n" +
//                "\\\\ud83c\\\\uddee\\\\ud83c\\\\udde9\n" +
//                "\\\\ud83c\\\\uddee\\\\ud83c\\\\uddf7\n" +
//                "\\\\ud83c\\\\uddee\\\\ud83c\\\\uddf6\n" +
//                "\\\\ud83c\\\\uddee\\\\ud83c\\\\uddea\n" +
//                "\\\\ud83c\\\\uddee\\\\ud83c\\\\uddf2\n" +
//                "\\\\ud83c\\\\uddee\\\\ud83c\\\\uddf1\n" +
//                "\\\\ud83c\\\\uddee\\\\ud83c\\\\uddf9\n" +
//                "\\\\ud83c\\\\uddef\\\\ud83c\\\\uddf2\n" +
//                "\\\\ud83c\\\\uddef\\\\ud83c\\\\uddf5\n" +
//                "\\\\ud83c\\\\udf8c\n" +
//                "\\\\ud83c\\\\uddef\\\\ud83c\\\\uddea\n" +
//                "\\\\ud83c\\\\uddef\\\\ud83c\\\\uddf4\n" +
//                "\\\\ud83c\\\\uddf0\\\\ud83c\\\\uddff\n" +
//                "\\\\ud83c\\\\uddf0\\\\ud83c\\\\uddea\n" +
//                "\\\\ud83c\\\\uddf0\\\\ud83c\\\\uddee\n" +
//                "\\\\ud83c\\\\uddfd\\\\ud83c\\\\uddf0\n" +
//                "\\\\ud83c\\\\uddf0\\\\ud83c\\\\uddfc\n" +
//                "\\\\ud83c\\\\uddf0\\\\ud83c\\\\uddec\n" +
//                "\\\\ud83c\\\\uddf1\\\\ud83c\\\\udde6\n" +
//                "\\\\ud83c\\\\uddf1\\\\ud83c\\\\uddfb\n" +
//                "\\\\ud83c\\\\uddf1\\\\ud83c\\\\udde7\n" +
//                "\\\\ud83c\\\\uddf1\\\\ud83c\\\\uddf8\n" +
//                "\\\\ud83c\\\\uddf1\\\\ud83c\\\\uddf7\n" +
//                "\\\\ud83c\\\\uddf1\\\\ud83c\\\\uddfe\n" +
//                "\\\\ud83c\\\\uddf1\\\\ud83c\\\\uddee\n" +
//                "\\\\ud83c\\\\uddf1\\\\ud83c\\\\uddf9\n" +
//                "\\\\ud83c\\\\uddf1\\\\ud83c\\\\uddfa\n" +
//                "\\\\ud83c\\\\uddf2\\\\ud83c\\\\uddf4\n" +
//                "\\\\ud83c\\\\uddf2\\\\ud83c\\\\uddf0\n" +
//                "\\\\ud83c\\\\uddf2\\\\ud83c\\\\uddec\n" +
//                "\\\\ud83c\\\\uddf2\\\\ud83c\\\\uddfc\n" +
//                "\\\\ud83c\\\\uddf2\\\\ud83c\\\\uddfe\n" +
//                "\\\\ud83c\\\\uddf2\\\\ud83c\\\\uddfb\n" +
//                "\\\\ud83c\\\\uddf2\\\\ud83c\\\\uddf1\n" +
//                "\\\\ud83c\\\\uddf2\\\\ud83c\\\\uddf9\n" +
//                "\\\\ud83c\\\\uddf2\\\\ud83c\\\\udded\n" +
//                "\\\\ud83c\\\\uddf2\\\\ud83c\\\\uddf6\n" +
//                "\\\\ud83c\\\\uddf2\\\\ud83c\\\\uddf7\n" +
//                "\\\\ud83c\\\\uddf2\\\\ud83c\\\\uddfa\n" +
//                "\\\\ud83c\\\\uddfe\\\\ud83c\\\\uddf9\n" +
//                "\\\\ud83c\\\\uddf2\\\\ud83c\\\\uddfd\n" +
//                "\\\\ud83c\\\\uddeb\\\\ud83c\\\\uddf2\n" +
//                "\\\\ud83c\\\\uddf2\\\\ud83c\\\\udde9\n" +
//                "\\\\ud83c\\\\uddf2\\\\ud83c\\\\udde8\n" +
//                "\\\\ud83c\\\\uddf2\\\\ud83c\\\\uddf3\n" +
//                "\\\\ud83c\\\\uddf2\\\\ud83c\\\\uddea\n" +
//                "\\\\ud83c\\\\uddf2\\\\ud83c\\\\uddf8\n" +
//                "\\\\ud83c\\\\uddf2\\\\ud83c\\\\udde6\n" +
//                "\\\\ud83c\\\\uddf2\\\\ud83c\\\\uddff\n" +
//                "\\\\ud83c\\\\uddf2\\\\ud83c\\\\uddf2\n" +
//                "\\\\ud83c\\\\uddf3\\\\ud83c\\\\udde6\n" +
//                "\\\\ud83c\\\\uddf3\\\\ud83c\\\\uddf7\n" +
//                "\\\\ud83c\\\\uddf3\\\\ud83c\\\\uddf5\n" +
//                "\\\\ud83c\\\\uddf3\\\\ud83c\\\\uddf1\n" +
//                "\\\\ud83c\\\\uddf3\\\\ud83c\\\\udde8\n" +
//                "\\\\ud83c\\\\uddf3\\\\ud83c\\\\uddff\n" +
//                "\\\\ud83c\\\\uddf3\\\\ud83c\\\\uddee\n" +
//                "\\\\ud83c\\\\uddf3\\\\ud83c\\\\uddea\n" +
//                "\\\\ud83c\\\\uddf3\\\\ud83c\\\\uddec\n" +
//                "\\\\ud83c\\\\uddf3\\\\ud83c\\\\uddfa\n" +
//                "\\\\ud83c\\\\uddf3\\\\ud83c\\\\uddeb\n" +
//                "\\\\ud83c\\\\uddf2\\\\ud83c\\\\uddf5\n" +
//                "\\\\ud83c\\\\uddf0\\\\ud83c\\\\uddf5\n" +
//                "\\\\ud83c\\\\uddf3\\\\ud83c\\\\uddf4\n" +
//                "\\\\ud83c\\\\uddf4\\\\ud83c\\\\uddf2\n" +
//                "\\\\ud83c\\\\uddf5\\\\ud83c\\\\uddf0\n" +
//                "\\\\ud83c\\\\uddf5\\\\ud83c\\\\uddfc\n" +
//                "\\\\ud83c\\\\uddf5\\\\ud83c\\\\uddf8\n" +
//                "\\\\ud83c\\\\uddf5\\\\ud83c\\\\udde6\n" +
//                "\\\\ud83c\\\\uddf5\\\\ud83c\\\\uddec\n" +
//                "\\\\ud83c\\\\uddf5\\\\ud83c\\\\uddfe\n" +
//                "\\\\ud83c\\\\uddf5\\\\ud83c\\\\uddea\n" +
//                "\\\\ud83c\\\\uddf5\\\\ud83c\\\\udded\n" +
//                "\\\\ud83c\\\\uddf5\\\\ud83c\\\\uddf3\n" +
//                "\\\\ud83c\\\\uddf5\\\\ud83c\\\\uddf1\n" +
//                "\\\\ud83c\\\\uddf5\\\\ud83c\\\\uddf9\n" +
//                "\\\\ud83c\\\\uddf5\\\\ud83c\\\\uddf7\n" +
//                "\\\\ud83c\\\\uddf6\\\\ud83c\\\\udde6\n" +
//                "\\\\ud83c\\\\uddf7\\\\ud83c\\\\uddea\n" +
//                "\\\\ud83c\\\\uddf7\\\\ud83c\\\\uddf4\n" +
//                "\\\\ud83c\\\\uddf7\\\\ud83c\\\\uddfa\n" +
//                "\\\\ud83c\\\\uddf7\\\\ud83c\\\\uddfc\n" +
//                "\\\\ud83c\\\\udde7\\\\ud83c\\\\uddf1\n" +
//                "\\\\ud83c\\\\uddf8\\\\ud83c\\\\udded\n" +
//                "\\\\ud83c\\\\uddf0\\\\ud83c\\\\uddf3\n" +
//                "\\\\ud83c\\\\uddf1\\\\ud83c\\\\udde8\n" +
//                "\\\\ud83c\\\\uddf5\\\\ud83c\\\\uddf2\n" +
//                "\\\\ud83c\\\\uddfb\\\\ud83c\\\\udde8\n" +
//                "\\\\ud83c\\\\uddfc\\\\ud83c\\\\uddf8\n" +
//                "\\\\ud83c\\\\uddf8\\\\ud83c\\\\uddf2\n" +
//                "\\\\ud83c\\\\uddf8\\\\ud83c\\\\uddf9\n" +
//                "\\\\ud83c\\\\uddf8\\\\ud83c\\\\udde6\n" +
//                "\\\\ud83c\\\\uddf8\\\\ud83c\\\\uddf3\n" +
//                "\\\\ud83c\\\\uddf7\\\\ud83c\\\\uddf8\n" +
//                "\\\\ud83c\\\\uddf8\\\\ud83c\\\\udde8\n" +
//                "\\\\ud83c\\\\uddf8\\\\ud83c\\\\uddf1\n" +
//                "\\\\ud83c\\\\uddf8\\\\ud83c\\\\uddec\n" +
//                "\\\\ud83c\\\\uddf8\\\\ud83c\\\\uddfd\n" +
//                "\\\\ud83c\\\\uddf8\\\\ud83c\\\\uddf0\n" +
//                "\\\\ud83c\\\\uddf8\\\\ud83c\\\\uddee\n" +
//                "\\\\ud83c\\\\uddf8\\\\ud83c\\\\udde7\n" +
//                "\\\\ud83c\\\\uddf8\\\\ud83c\\\\uddf4\n" +
//                "\\\\ud83c\\\\uddff\\\\ud83c\\\\udde6\n" +
//                "\\\\ud83c\\\\uddec\\\\ud83c\\\\uddf8\n" +
//                "\\\\ud83c\\\\uddf0\\\\ud83c\\\\uddf7\n" +
//                "\\\\ud83c\\\\uddf8\\\\ud83c\\\\uddf8\n" +
//                "\\\\ud83c\\\\uddea\\\\ud83c\\\\uddf8\n" +
//                "\\\\ud83c\\\\uddf1\\\\ud83c\\\\uddf0\n" +
//                "\\\\ud83c\\\\uddf8\\\\ud83c\\\\udde9\n" +
//                "\\\\ud83c\\\\uddf8\\\\ud83c\\\\uddf7\n" +
//                "\\\\ud83c\\\\uddf8\\\\ud83c\\\\uddff\n" +
//                "\\\\ud83c\\\\uddf8\\\\ud83c\\\\uddea\n" +
//                "\\\\ud83c\\\\udde8\\\\ud83c\\\\udded\n" +
//                "\\\\ud83c\\\\uddf8\\\\ud83c\\\\uddfe\n" +
//                "\\\\ud83c\\\\uddf9\\\\ud83c\\\\uddfc\n" +
//                "\\\\ud83c\\\\uddf9\\\\ud83c\\\\uddef\n" +
//                "\\\\ud83c\\\\uddf9\\\\ud83c\\\\uddff\n" +
//                "\\\\ud83c\\\\uddf9\\\\ud83c\\\\udded\n" +
//                "\\\\ud83c\\\\uddf9\\\\ud83c\\\\uddf1\n" +
//                "\\\\ud83c\\\\uddf9\\\\ud83c\\\\uddec\n" +
//                "\\\\ud83c\\\\uddf9\\\\ud83c\\\\uddf0\n" +
//                "\\\\ud83c\\\\uddf9\\\\ud83c\\\\uddf4\n" +
//                "\\\\ud83c\\\\uddf9\\\\ud83c\\\\uddf9\n" +
//                "\\\\ud83c\\\\uddf9\\\\ud83c\\\\uddf3\n" +
//                "\\\\ud83c\\\\uddf9\\\\ud83c\\\\uddf7\n" +
//                "\\\\ud83c\\\\uddf9\\\\ud83c\\\\uddf2\n" +
//                "\\\\ud83c\\\\uddf9\\\\ud83c\\\\udde8\n" +
//                "\\\\ud83c\\\\uddf9\\\\ud83c\\\\uddfb\n" +
//                "\\\\ud83c\\\\uddfa\\\\ud83c\\\\uddec\n" +
//                "\\\\ud83c\\\\uddfa\\\\ud83c\\\\udde6\n" +
//                "\\\\ud83c\\\\udde6\\\\ud83c\\\\uddea\n" +
//                "\\\\ud83c\\\\uddec\\\\ud83c\\\\udde7\n" +
//                "\\\\ud83c\\\\uddfa\\\\ud83c\\\\uddf8\n" +
//                "\\\\ud83c\\\\uddfb\\\\ud83c\\\\uddee\n" +
//                "\\\\ud83c\\\\uddfa\\\\ud83c\\\\uddfe\n" +
//                "\\\\ud83c\\\\uddfa\\\\ud83c\\\\uddff\n" +
//                "\\\\ud83c\\\\uddfb\\\\ud83c\\\\uddfa\n" +
//                "\\\\ud83c\\\\uddfb\\\\ud83c\\\\udde6\n" +
//                "\\\\ud83c\\\\uddfb\\\\ud83c\\\\uddea\n" +
//                "\\\\ud83c\\\\uddfb\\\\ud83c\\\\uddf3\n" +
//                "\\\\ud83c\\\\uddfc\\\\ud83c\\\\uddeb\n" +
//                "\\\\ud83c\\\\uddea\\\\ud83c\\\\udded\n" +
//                "\\\\ud83c\\\\uddfe\\\\ud83c\\\\uddea\n" +
//                "\\\\ud83c\\\\uddff\\\\ud83c\\\\uddf2\n" +
//                "\\\\ud83c\\\\uddff\\\\ud83c\\\\uddfc"


    )

    fun showLoading() {
        loading.value = true
    }

    fun hideLoading() {
        loading.value = false
    }

    suspend fun sortList() = withContext(Dispatchers.Main) {
        //Heavy work
    }
}
