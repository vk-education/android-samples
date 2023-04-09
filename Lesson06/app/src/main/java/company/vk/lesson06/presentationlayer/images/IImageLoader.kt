package company.vk.Lesson06.presentationlayer.images

import android.widget.ImageView

interface IImageLoader {
	fun loadImage(target: ImageView?, url: String?)
}