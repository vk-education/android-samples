package com.example.lecture06.data.converter

import com.example.lecture06.data.remote.models.country.CountriesResponse
import com.example.lecture06.domain.models.Country

class CountriesResponseToCountriesConverter : OneWayConverter<CountriesResponse, List<Country>> {
    override fun convert(from: CountriesResponse): List<Country> =
        from.countries.take(1).map(::Country)
}