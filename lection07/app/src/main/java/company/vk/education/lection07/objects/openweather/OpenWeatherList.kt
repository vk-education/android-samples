package company.vk.education.lection07.objects.openweather

import com.google.gson.annotations.SerializedName
import company.vk.education.lection07.objects.AbstractObject

class OpenWeatherList<TYPE>: AbstractObject() {
    @SerializedName("message") var message = ""
    @SerializedName("cod") var cod = ""
    @SerializedName("count") var count = -1
    @SerializedName("list") var list = emptyList<TYPE>()
}