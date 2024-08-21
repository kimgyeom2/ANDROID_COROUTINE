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

    fun aa(){viewModelScope.launch { _uiState.value=UiState.Success("고양이") }}
    fun bb(){viewModelScope.launch { _uiState.value=UiState.Success("당나귀")
        Log.e("gyeom","xxxxxxx")}}
    fun cc(){viewModelScope.launch { _uiState.value=UiState.Success("스컹크") }}
    fun dd(){viewModelScope.launch { _uiState.value=UiState.Success("맘모스") }}
}