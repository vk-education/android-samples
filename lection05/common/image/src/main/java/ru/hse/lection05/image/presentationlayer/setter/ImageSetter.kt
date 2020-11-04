package ru.hse.lection05.image.presentationlayer.setter

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

class ImageSetter(context: Context) {
    val glide = Glide.with(context)

    fun loadGif(target: ImageView, url: String) {
        glide
            .load(url)
            .into(target)
    }
}