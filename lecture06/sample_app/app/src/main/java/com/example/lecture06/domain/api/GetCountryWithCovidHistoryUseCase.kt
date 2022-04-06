package com.example.lecture06.domain.api

import com.example.lecture06.domain.models.CountryCovidHistory
import com.example.lecture06.domain.models.common.Result

interface GetCountryWithCovidHistoryUseCase {

    suspend operator fun invoke(): Result<List<CountryCovidHistory>>
}