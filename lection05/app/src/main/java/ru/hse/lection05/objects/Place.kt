package ru.hse.lection05.objects

import com.google.gson.annotations.SerializedName

class Place: AbstractObject() {
    @SerializedName("id") var id = 0L
    @SerializedName("name") var name = ""

    @SerializedName("coord") var coord: Coord? = null
    @SerializedName("sys") var sys: Sys? = null


    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return other is Place
                && other.id == id
    }



}