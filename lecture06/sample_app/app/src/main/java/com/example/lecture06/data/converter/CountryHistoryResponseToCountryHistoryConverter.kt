package com.example.lecture06.data.converter

import com.example.lecture06.data.remote.models.history.CovidHistoryResponse
import com.example.lecture06.domain.models.Cases
import com.example.lecture06.domain.models.CovidHistory
import com.example.lecture06.domain.models.DailyCases

class CountryHistoryResponseToCountryHistoryConverter :
    OneWayConverter<CovidHistoryResponse, CovidHistory> {
    override fun convert(from: CovidHistoryResponse): CovidHistory {
        val response = from.caseStaticResponses.first()
        val casesResponse = response.casesResponse
        val dailyCases = DailyCases(
            Cases(
                active = casesResponse.active ?: 0,
                critical = casesResponse.critical ?: 0,
                new = casesResponse.new ?: "0",
                recovered = casesResponse.recovered ?: 0,
                total = casesResponse.total ?: 0,
            ),
            response.day
        )

        return CovidHistory(dailyCases)
    }
}