package ru.example.myapplication.objects

class Item {
    var id = ""

    protected var images: Map<String, Image> = emptyMap()


    fun largeImage(): Image {
        return images[Image.TYPE_LARGE]!!
    }
}