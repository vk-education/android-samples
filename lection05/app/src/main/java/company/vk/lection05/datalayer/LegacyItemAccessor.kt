package company.vk.lection05.datalayer

import company.vk.lection05.businesslayer.AbstractItemProvider
import company.vk.lection05.objects.Item
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

@Deprecated("Не используйте эту механику. Она потребует дополнительных действий")
internal class LegacyItemAccessor(
	val baseUrl: String
): IItemAccessor {
	override fun items(): List<Item> {
		val url = "$baseUrl/api/cats?skip=0&limit=100"
		val response = get(url)
		val result = parseResponse(response)

		return result
	}

	fun get(url: String): String {
		var connection: HttpURLConnection? = null
		try {
			connection = createConnection(url)
			connection.connect()

			val responseCode = connection.responseCode
			if (responseCode == HttpURLConnection.HTTP_OK) {
				return downloadBody(connection.inputStream)
			} else {
				val errorResponse = downloadBody(connection.errorStream)
				throw IllegalStateException(errorResponse)
			}
		} finally {
			connection?.disconnect()
		}
	}

	protected fun addHeaders(connection: HttpURLConnection, headers: Map<String, String>) {
		headers.forEach {
			connection.setRequestProperty(it.key, it.value)
		}
	}

	protected fun createConnection(
		url: String,
		followRedirects: Boolean = false
	): HttpURLConnection {
		val connection = URL(url).openConnection() as HttpURLConnection

		connection.apply {
			connectTimeout = TIMEOUT_CONNECT
			readTimeout = TIMEOUT_READ
			instanceFollowRedirects = followRedirects
		}

		return connection
	}

	protected fun uploadBody(stream: OutputStream, body: String) {
		val writer = BufferedWriter(OutputStreamWriter(stream, "UTF-8"))

		try {
			writer.write(body)
			writer.flush()
			writer.close()
			stream.close()
		} finally {
			writer.close()
		}
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
			result
		}
	}


	protected fun parseResponse(response: String): List<Item> {
		val jsonResponse = JSONArray(response)

		val list = mutableListOf<Item>()

		for (index in 0 until jsonResponse.length()) {
			val jsonItem = jsonResponse.getJSONObject(index)
			val item = parseItem(jsonItem)
			list.add(item)
		}

		return list
	}


	protected fun parseItem(jsonItem: JSONObject): Item {
		val id = jsonItem.getString("_id")
		val item = Item().apply {
			this.id = id
			value = "$baseUrl/cat/$id"
		}

		return item
	}

	companion object {
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