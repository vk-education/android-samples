package company.vk.base.images.glide.presentationlayer

import android.widget.ImageView
import com.bumptech.glide.Glide
import company.vk.base.images.presentationlayer.IImageLoader

class GlideImageLoader: IImageLoader {
	override fun loadImage(view: ImageView?, source: Any?) {
		if (view == null) {
			return
		}

		view.background = null

		if (source == null) {
			return
		}

		Glide.with(view)
			.load(source)
			.into(view)
	}
}