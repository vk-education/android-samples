package company.vk.base.images.presentationlayer

import android.widget.ImageView

interface IImageLoader {
	fun loadImage(view: ImageView?, source: Any?)
}