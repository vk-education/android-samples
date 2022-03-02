package ru.example.myapplication.presentationlayer.images

import android.widget.ImageView
import com.bumptech.glide.Glide

class ImageLoader {
    fun loadGif(url: String?, imageView: ImageView?) {
        if (imageView == null) {
            return
        }

        imageView.setImageDrawable(null)

        if (url.isNullOrBlank()) {
            return
        }

        Glide.with(imageView)
            .asGif()
            .load(url)
            .into(imageView)
    }
}