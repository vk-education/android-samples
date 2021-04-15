package ru.hse.lection05.objects

import com.google.gson.annotations.SerializedName

class WeatherData: AbstractObject() {
    @SerializedName("id") var id = 0L
    @SerializedName("name") var name = ""

    @SerializedName("weather") var weather = emptyList<Weather>()
    @SerializedName("main") var main: Main? = null



    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return other is WeatherData
                && other.id == id
    }
}