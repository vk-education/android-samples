package ru.hse.lection05.api.giphy.objects

import com.google.gson.annotations.SerializedName

class ListResult {
    @SerializedName("data") var data = listOf<Item>()
    @SerializedName("meta") lateinit var meta: Meta
    @SerializedName("pagination") lateinit var pagination: Pagination
}