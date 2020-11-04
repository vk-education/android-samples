package ru.hse.lection05.api.giphy.objects

import com.google.gson.annotations.SerializedName

class Pagination {
    @SerializedName("count") var count = 0
    @SerializedName("offset") var offset = 0
}