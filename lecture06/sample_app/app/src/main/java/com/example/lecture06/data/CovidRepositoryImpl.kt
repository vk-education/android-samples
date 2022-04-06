package com.example.lecture06.data

import com.example.lecture06.coroutines.CoroutineDispatchers
import com.example.lecture06.data.converter.CountriesResponseToCountriesConverter
import com.example.lecture06.data.converter.CountryHistoryResponseToCountryHistoryConverter
import com.example.lecture06.data.remote.CovidApi
import com.example.lecture06.domain.api.CovidRepository
import com.example.lecture06.domain.models.Country
import com.example.lecture06.domain.models.CovidHistory
import kotlinx.coroutines.withContext
import java.util.*

internal class CovidRepositoryImpl(
    private val dispatchers: CoroutineDispatchers,
    private val covidApi: CovidApi,
    private val countryHistoryConverter: CountryHistoryResponseToCountryHistoryConverter,
    private val countriesConverter: CountriesResponseToCountriesConverter
) : CovidRepository {

    override suspend fun getCountries(): List<Country> = withContext(dispatchers.io()) {
        countriesConverter.convert(covidApi.getCountries().body()!!)
    }


    override suspend fun getCountryHistory(country: Country, date: Date): CovidHistory =
        withContext(dispatchers.io()) {
            countryHistoryConverter.convert(
                covidApi.getCountryHistory(
                    country.name,
                    date.toFormat()
                ).body()!!
            )
        }
}
