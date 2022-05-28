package com.lionzxy.jetpackcompose.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

private const val KEY_COUNTER = "key_counter"

class CounterViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val numberLiveData = savedStateHandle.getLiveData<Int>(KEY_COUNTER)
    fun getNumberState(): LiveData<Int> = numberLiveData

    fun increment() {
        val number = numberLiveData.value ?: 0
        savedStateHandle[KEY_COUNTER] = number + 1
    }
}
