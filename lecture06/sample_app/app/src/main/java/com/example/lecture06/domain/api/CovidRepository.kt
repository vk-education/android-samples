package com.example.lecture06.domain.api

import com.example.lecture06.domain.models.Country
import com.example.lecture06.domain.models.CovidHistory
import java.util.*

interface CovidRepository {
    suspend fun getCountries(): List<Country>
    suspend fun getCountryHistory(country: Country, date: Date): CovidHistory
}