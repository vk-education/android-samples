package company.vk.lection05.datalayer

import com.google.gson.GsonBuilder
import company.vk.lection05.objects.Item
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Deprecated("Этот класс осздан просто для совместимости интерфейсов. Такое в рабочем проекте не используется")
class ItemAccessorBridge private constructor(val accessor: IItemAccessor2) : IItemAccessor {
	override fun items(): List<Item> = runBlocking {
		accessor.items2()
	}


	companion object {
		fun create(baseUrl: String): ItemAccessorBridge {
			val gson = GsonBuilder().apply {
				// Добавляем кастомную фабрику объектов. В нашем случае она требуется.
				// Обычно все разруливается аннотациями Gson в объекте для парсинга
				registerTypeAdapter(Item::class.java, CustomItemGsonDeserializer(baseUrl))
			}.create()

			// Добавляется исключительно для удобства просмотров запросов
			val loggingInterceptor = HttpLoggingInterceptor().apply {
				level = HttpLoggingInterceptor.Level.BODY
			}

			val okHttpClient = OkHttpClient.Builder().apply {
				addNetworkInterceptor(loggingInterceptor)
			}.build()

			val retrofit = Retrofit.Builder().apply {
				addConverterFactory(GsonConverterFactory.create(gson))
				client(okHttpClient)
				baseUrl(baseUrl)
			}.build()

			val accessor = retrofit.create(IItemAccessor2::class.java)
			val bridge = ItemAccessorBridge(accessor)

			return bridge
		}
	}
}