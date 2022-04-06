package com.example.lecture06.data.remote.models.history

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CaseStaticResponse(
    @Json(name = "cases") val casesResponse: CasesResponse,
    @Json(name = "day") val day: String,
)