package com.example.lecture06.data.remote

import com.example.lecture06.data.remote.models.country.CountriesResponse
import com.example.lecture06.data.remote.models.history.CovidHistoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CovidApi {

    @GET("/countries")
    suspend fun getCountries(): Response<CountriesResponse>

    @GET("/history")
    suspend fun getCountryHistory(
        @Query("country") country: String,
        @Query("day") day: String
    ): Response<CovidHistoryResponse>
}