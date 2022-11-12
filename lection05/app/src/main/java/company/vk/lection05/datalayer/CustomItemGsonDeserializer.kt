package company.vk.lection05.datalayer

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import company.vk.lection05.objects.Item
import java.lang.reflect.Type

@Deprecated("Данный объект создан из-за того, что мы url картинки формируем из id")
class CustomItemGsonDeserializer(val baseUrl: String): JsonDeserializer<Item> {
	override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Item {
		val jsonObject = json!!.asJsonObject
		val id = jsonObject.get("_id").asString
		val item = Item().apply {
			this.id = id
			value = "$baseUrl/cat/$id"
		}

		return item
	}
}