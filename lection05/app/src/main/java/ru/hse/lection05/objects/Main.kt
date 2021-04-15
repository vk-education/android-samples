package ru.hse.lection05.objects

import com.google.gson.annotations.SerializedName

class Main: AbstractObject() {
    @SerializedName("temp") var temp = 0.0
}