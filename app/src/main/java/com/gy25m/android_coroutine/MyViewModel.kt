package com.gy25m.android_coroutine

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class MyViewModel : ViewModel() {
     val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()


    init {
        viewModelScope.launch {
            _uiState.value=UiState.Loading
        }
    }
    fun loadData() {
        viewModelScope.launch {
            try {
                val data = fetchDataFromRepository()
                _uiState.value=UiState.Success(data)
            } catch (e: Exception) {
                _uiState.value=UiState.Error("Failed to load data")
            }
        }
    }

    fun loading(){
        var zz=viewModelScope.launch {
            Log.e("gyeom","zz")
            delay(2000)
            _uiState.value=UiState.Loading
        }
    }
    private suspend fun fetchDataFromRepository(): String {
        return withContext(Dispatchers.IO) {
            // Simulate network call
            Log.e("gyeom","xx")
            Thread.sleep(2000)
            loading()

            "Hello, World!"
        }
    }

//    fun andyLotteSend(){
//        private var sending = false
//
//            if (sending) {
//                return
//            }
//            viewModelScope.launch(Dispatchers.IO) {
//                try {
//                    val unSendList = infoUseCase.getUnSentDataToday() // 로컬 미전송데이터 조회
//                    if (unSendList.isEmpty()){
//                        toast(R.string.data_send_no)
//                        return@launch
//                    }
//                    sending=true
//                    showLoading()
//
//                    // 서버 업로드 성공 후 로컬 업데이트
//                    uploadServerUseCase.invoke(unSendList).let {
//                        if (it.code == "00") { // todo : 빈 문자열을 전송해도 code가 00으로 떨어짐
//                            infoUseCase.updateSendY() // 로컬 전송 상태 변경, N->Y
//                            toast(R.string.data_send_success)
//                        } else {
//                            throw Exception(it.message)
//                        }
//                    }
//                } catch (_:CancellationException){
//
//                }catch (e: Exception) {
//                    hideLoading()
//                    withContext(Dispatchers.Main) {
//                        toast(R.string.data_send_fail)
//                    }
//                    createLogFile("$menu 데이터 전송", e.toString())
//                    return@launch
//                }finally {
//                    // 작업이 완료되었을 때 처리
//                    sending = false
//                    hideLoading()
//                }
//            }
//
//    }

    fun aa(){viewModelScope.launch { _uiState.value=UiState.Success("고양이") }}
    fun bb(){viewModelScope.launch { _uiState.value=UiState.Success("당나귀")
        Log.e("gyeom","xxxxxxx")}}
    fun cc(){viewModelScope.launch { _uiState.value=UiState.Success("스컹크") }}
    fun dd(){viewModelScope.launch { _uiState.value=UiState.Success("맘모스") }}
}