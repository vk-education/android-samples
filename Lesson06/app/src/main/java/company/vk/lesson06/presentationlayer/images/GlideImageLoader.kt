package company.vk.Lesson06.presentationlayer.images

import android.widget.ImageView
import com.bumptech.glide.Glide

class GlideImageLoader: IImageLoader {
	override fun loadImage(target: ImageView?, url: String?) {
		if (target == null) {
			return
		}

		if (url.isNullOrBlank()) {
			target.setImageDrawable(null)
			return
		}

		Glide.with(target)
			.load(url)
			.into(target)
	}
}