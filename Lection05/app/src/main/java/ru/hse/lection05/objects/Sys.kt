package ru.hse.lection05.objects

import com.google.gson.annotations.SerializedName


class Sys: AbstractObject() {
    @SerializedName("country") var country = ""
}