package company.vk.base.films.retrofit

import com.google.gson.GsonBuilder
import company.vk.base.films.businesslayer.FilmProvider
import company.vk.base.films.businesslayer.IFilmProvider
import company.vk.base.films.datalayer.IApiAccessor
import company.vk.base.films.retrofit.datalayer.IApiRetrofitAccessor
import company.vk.common.ServiceLocator
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class AbstractFilmRetrofitFactory: ServiceLocator.IFactory {
	override fun create(clazz: Class<*>): Any? {
		return when {
			clazz.isAssignableFrom(IFilmProvider::class.java) -> FilmProvider()
			clazz.isAssignableFrom(IApiAccessor::class.java) -> createRetrofitAccessor()
			else -> null
		}
	}


	protected open fun createClientBuilder(): OkHttpClient.Builder {
		return OkHttpClient.Builder()
	}

	protected fun createRetrofitAccessor(): IApiRetrofitAccessor {
		val client = createClientBuilder().build()

		val gson = GsonBuilder()
			.excludeFieldsWithoutExposeAnnotation()
			.create()

		val retrofit = Retrofit.Builder()
			.baseUrl("https://ghibliapi.herokuapp.com/")
			.addConverterFactory(GsonConverterFactory.create(gson))
			.client(client)
			.build()

		val api = retrofit.create(IApiRetrofitAccessor::class.java)

		return api
	}
}