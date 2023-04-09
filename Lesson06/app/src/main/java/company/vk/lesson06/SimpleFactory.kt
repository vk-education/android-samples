package company.vk.Lesson06

import android.content.Context
import company.vk.Lesson06.businesslayer.FactorialCalculator
import company.vk.Lesson06.businesslayer.ICalculator
import company.vk.Lesson06.datalyaer.ICharacterAccessor
import company.vk.Lesson06.presentationlayer.images.GlideImageLoader
import company.vk.Lesson06.presentationlayer.images.IImageLoader
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SimpleFactory: ServiceLocator.IFactory {
	protected val retrofit by lazy { createRetrofit() }
	protected val imageLoader by lazy { createImageLoader() }

	override fun <T> inject(clazz: Class<T>): T? {
		val instance = when {
			clazz.isAssignableFrom(ICalculator::class.java) -> FactorialCalculator()
			clazz.isAssignableFrom(ICharacterAccessor::class.java) -> retrofit.create(ICharacterAccessor::class.java)
			clazz.isAssignableFrom(IImageLoader::class.java) -> imageLoader
			else -> null
		}

		return instance as? T
	}


	protected fun createRetrofit(): Retrofit {
		val baseUrl = ServiceLocator.inject<Context>().getString(R.string.base_url)

		val loggingInterceptor = HttpLoggingInterceptor().apply {
			level = HttpLoggingInterceptor.Level.BODY
		}

		val client = OkHttpClient.Builder().apply {
			addInterceptor(loggingInterceptor)
		}.build()

		val retrofit = Retrofit.Builder().apply {
			addConverterFactory(GsonConverterFactory.create())
			baseUrl(baseUrl)
			client(client)
		}.build()

		return retrofit
	}

	protected fun createImageLoader(): IImageLoader {
		return GlideImageLoader()
	}
}