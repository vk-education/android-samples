package company.vk.base.films.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class FilmRetrofitFactory: AbstractFilmRetrofitFactory() {
	override fun createClientBuilder(): OkHttpClient.Builder {
		val loggingInterceptor = HttpLoggingInterceptor().apply {
			level = HttpLoggingInterceptor.Level.BODY
		}

		return super.createClientBuilder()
			.addNetworkInterceptor(loggingInterceptor)
	}
}