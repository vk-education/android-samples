package company.vk.base.films.simple.datalayer

import company.vk.base.films.datalayer.IApiAccessor
import company.vk.base.films.objects.Film
import company.vk.base.loggers.Logger
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class SimpleApiAccessor(val baseUrl: String): IApiAccessor {
	override suspend fun filmList(): List<Film> {
		val connection = createConnection("films")
		checkPhoneResponse(connection)
		val responseBody = downloadBody(connection.inputStream)
		val jsonArray = JSONArray(responseBody)
		val length = jsonArray.length()

		val result = mutableListOf<Film>()
		for (index in 0 until length) {
			val json = jsonArray.optJSONObject(index)
			val film = Film().apply {
				id = json.optString("id")
				title = json.optString("title")
				originalTitle = json.optString("original_title")
				image = json.optString("image")
				releaseDate = json.optString("release_date")
				runningTime = json.optString("running_time")
				rtScore = json.optString("rt_score")
			}

			result.add(film)
		}

		return result
	}

	protected fun checkPhoneResponse(connection: HttpURLConnection) {
		connection.connect()
		val code = connection.responseCode

		when(code) {
			HTTP_CODE_200 -> Unit

			else -> throw IllegalStateException("can't handle http code: $code")
		}
	}

	protected fun createConnection(path: String): HttpURLConnection {
		val url = baseUrl + path

		val connection = URL(url).openConnection() as HttpURLConnection
		Logger.d("connection url ${connection.url}", "TECH")

		connection.apply {
			connectTimeout = TIMEOUT_CONNECT
			readTimeout = TIMEOUT_READ
			instanceFollowRedirects = false
		}

		return connection
	}

	protected fun downloadBody(stream: InputStream): String {
		val reader = BufferedReader(InputStreamReader(stream))

		return reader.use {
			val total = StringBuilder()
			var line: String?
			while (reader.readLine().also { line = it } != null) {
				total.append(line).append('\n')
			}
			val result = total.toString()
			Logger.d("response $result", "TECH")

			result
		}
	}


	companion object {
		const val TAG = "SimpleApiAccessor"


		protected const val TIMEOUT_CONNECT = 5000
		protected const val TIMEOUT_READ = 5000

		@JvmStatic protected val HTTP_CODE_200 = 200
		@JvmStatic protected val HTTP_CODE_201 = 201
		@JvmStatic protected val HTTP_CODE_302 = 302
		@JvmStatic protected val HTTP_CODE_400 = 400

		@JvmStatic protected val HTTP_METHOD_GET = "GET"
		@JvmStatic protected val HTTP_METHOD_POST = "POST"

		@JvmStatic protected val APPLICATION_JSON = "application/json"
		@JvmStatic protected val APPLICATION_FORM = "application/x-www-form-urlencoded"
	}
}