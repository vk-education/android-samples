package ru.hse.lection05.base.presentationlayer.adapters

import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import ru.hse.lection05.api.giphy.objects.Item
import ru.hse.lection05.base.R
import ru.hse.lection05.image.presentationlayer.setter.ImageSetter

class ItemHolder(itemView: View, val listener: IListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    interface IListener {
        fun onItemClicked(view: View, item: Item)
    }


    protected val image = itemView.findViewById<ImageView>(R.id.image)
    protected val imageSetter = ImageSetter(itemView.context)

    protected var item: Item? = null


    init {
        image.setOnClickListener(this)
    }


    override fun onClick(v: View) {
        item?.let {
            listener.onItemClicked(v, it)
        }
    }


    fun bind(item: Item) {
        if (this.item == item) {
            return
        }

        this.item = item

        val params = image.layoutParams as ConstraintLayout.LayoutParams
        params.dimensionRatio = "${item.images.item.width}:${item.images.item.height}"
        image.layoutParams = params

        imageSetter.loadGif(image, item.images.item.url)
    }
}