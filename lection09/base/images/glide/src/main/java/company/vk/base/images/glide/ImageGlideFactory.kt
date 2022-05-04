package company.vk.base.images.glide

import company.vk.base.images.glide.presentationlayer.GlideImageLoader
import company.vk.base.images.presentationlayer.IImageLoader
import company.vk.common.ServiceLocator

class ImageGlideFactory: ServiceLocator.IFactory {
	override fun create(clazz: Class<*>): Any? {
		return when {
			clazz.isAssignableFrom(IImageLoader::class.java) -> GlideImageLoader()
			else -> null
		}
	}
}