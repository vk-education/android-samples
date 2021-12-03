package ru.hse.lection05.objects

import com.google.gson.annotations.SerializedName

class Coord: AbstractObject() {
    @SerializedName("lat") var lat = 0.0
    @SerializedName("lon") var lon = 0.0
}