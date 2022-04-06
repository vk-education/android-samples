package com.example.lecture06.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.lecture06.domain.api.GetCountryWithCovidHistoryUseCase
import com.example.lecture06.domain.models.common.Result
import com.example.lecture06.presentation.model.CountryCovidHistoryUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CountryCovidHistoryViewModel(
    private val getCountryWithCovidHistoryUseCase: GetCountryWithCovidHistoryUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<CountryCovidHistoryUIState>(CountryCovidHistoryUIState.Loading)
    val state = _state.asStateFlow()

    fun loadData() {
        viewModelScope.launch {
            _state.value = CountryCovidHistoryUIState.Loading
            _state.value = when (val result = getCountryWithCovidHistoryUseCase()) {
                is Result.Error -> CountryCovidHistoryUIState.Error
                is Result.Success -> CountryCovidHistoryUIState.Success(result.data)
            }
        }
    }

    class Factory(private val getCountryWithCovidHistoryUseCase: GetCountryWithCovidHistoryUseCase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            CountryCovidHistoryViewModel(getCountryWithCovidHistoryUseCase) as T
    }
}

