package com.example.lecture06.data.remote.models.country

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CountriesResponse(
    @Json(name = "response") val countries: List<String>
)