package com.vkeduction.lection03.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class TestViewModel : ViewModel() {
    val stateFlow = MutableStateFlow("Test")
}