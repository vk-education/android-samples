package ru.hse.lection05.api.giphy.objects

import com.google.gson.annotations.SerializedName

class Item {
    @SerializedName("type") var type = ""
    @SerializedName("id") var id = ""
    @SerializedName("url") var url = ""

    @SerializedName("images") lateinit var images: Images


    class Images {
        @SerializedName("downsized_large") lateinit var item: Image
    }

    class Image {
        @SerializedName("height") var height = ""
        @SerializedName("width") var width = ""
        @SerializedName("url") var url = ""
    }
}