package com.example.lecture06.data.remote.models.history

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CovidHistoryResponse(
    @Json(name = "response") val caseStaticResponses: List<CaseStaticResponse>,
)