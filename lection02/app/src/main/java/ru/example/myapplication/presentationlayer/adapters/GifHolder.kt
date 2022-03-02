package ru.example.myapplication.presentationlayer.adapters

import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import ru.example.myapplication.R
import ru.example.myapplication.ServiceLocator.inject
import ru.example.myapplication.objects.Item
import ru.example.myapplication.presentationlayer.images.ImageLoader

class GifHolder(view: View): RecyclerView.ViewHolder(view) {
    protected val image by lazy { itemView.findViewById<ImageView>(R.id.image) }
    protected val imageLoader by inject<ImageLoader>()


    fun bind(item: Item) {
        val gif = item.largeImage()

        val params = image.layoutParams as ConstraintLayout.LayoutParams
        params.dimensionRatio = "H,${gif.width}:${gif.height}"

        imageLoader.loadGif(gif.url, image)
    }
}