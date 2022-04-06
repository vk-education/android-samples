package com.example.lecture06.data.remote.models.history

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CasesResponse(
    @Json(name = "active") val active: Int?,
    @Json(name = "critical") val critical: Int?,
    @Json(name = "new") val new: String?,
    @Json(name = "recovered") val recovered: Int?,
    @Json(name = "total") val total: Int?
)