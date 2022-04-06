package com.example.lecture06.domain

import com.example.lecture06.domain.api.GetCountryWithCovidHistoryUseCase
import com.example.lecture06.domain.api.CovidRepository
import com.example.lecture06.domain.models.CountryCovidHistory
import com.example.lecture06.domain.models.common.Result
import com.example.lecture06.utils.CalendarUtils

class GetCountryWithCovidHistoryUseCaseImpl(
    private val covidRepository: CovidRepository
) : GetCountryWithCovidHistoryUseCase {

    override suspend operator fun invoke(): Result<List<CountryCovidHistory>> {
        return try {
            val countries = covidRepository.getCountries()

            val result = countries.mapNotNull { country ->
                try {
                    CountryCovidHistory(
                        country,
                        covidRepository.getCountryHistory(country, CalendarUtils.currentDate)
                    )
                } catch (e: Exception) {
                    null
                }
            }
            Result.Success(result)
        } catch (e: Exception) {
            Result.Error(e)
        }


    }
}