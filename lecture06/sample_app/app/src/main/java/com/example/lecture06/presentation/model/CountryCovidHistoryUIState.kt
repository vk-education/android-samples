package com.example.lecture06.presentation.model

import com.example.lecture06.domain.models.CountryCovidHistory

sealed interface CountryCovidHistoryUIState {
    object Loading : CountryCovidHistoryUIState
    data class Success(val data: List<CountryCovidHistory>) : CountryCovidHistoryUIState
    object Error : CountryCovidHistoryUIState
}