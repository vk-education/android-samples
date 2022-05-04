package company.vk.base.images.glide

import android.content.Context
import androidx.startup.Initializer
import company.vk.common.ServiceLocator

class ImageGlideInitializer: Initializer<ImageGlideFactory> {
	override fun create(context: Context): ImageGlideFactory {
		val factory = ImageGlideFactory()

		ServiceLocator.addFactory(factory)

		return factory
	}

	override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()
}