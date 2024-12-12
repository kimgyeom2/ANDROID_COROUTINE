package com.gy25m.android_coroutine

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap

class SwitchMap() {
    val invoice = MutableLiveData("")
    val pouchNumber = MutableLiveData("")
    val totalCount = MutableLiveData("0")
    val countByPouch = pouchNumber.switchMap {
        if (it.isNullOrBlank()) {
            MutableLiveData(0) // 기본값을 반환
        } else {
//            infoUseCase.getCountByPouch(WORKSCOPE_POUCH, it)
            MutableLiveData(0)
        }
    }
}