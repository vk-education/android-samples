package ru.hse.lection05.objects

import com.google.gson.annotations.SerializedName

class Weather: AbstractObject() {
    @SerializedName("id") var id = 0L
    @SerializedName("main") var main = ""
    @SerializedName("description") var description = ""
    @SerializedName("icon") var icon = ""
}