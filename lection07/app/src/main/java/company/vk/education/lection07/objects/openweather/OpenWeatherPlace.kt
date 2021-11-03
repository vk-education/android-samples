package company.vk.education.lection07.objects.openweather

import com.google.gson.annotations.SerializedName
import company.vk.education.lection07.objects.AbstractPlace

class OpenWeatherPlace: AbstractPlace() {
    @SerializedName("id") protected var _id = -1L
    @SerializedName("name") protected var _title = ""

    override fun id() = _id
    override fun title() = _title
}