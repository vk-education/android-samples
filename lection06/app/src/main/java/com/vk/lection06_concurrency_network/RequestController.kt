package com.vk.lection06_concurrency_network

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

interface RequestController {
    suspend fun requestJoke(): Result
}

sealed interface Result {
    data class Ok(val joke: Joke) : Result
    data class Error(val error: String) : Result
}

@Serializable
data class Joke(
    @SerialName("value") val joke: String,
    @SerialName("created_at") @Serializable(with = DateSerializer::class) val created: Date,
)

@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = Date::class)
object DateSerializer {
    private val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)

    override fun deserialize(decoder: Decoder): Date {
        val dateString = decoder.decodeString()
        return format.parse(dateString)!!
    }

    override fun serialize(encoder: Encoder, value: Date) {
        val dateString = format.format(value)
        encoder.encodeString(dateString)
    }
}

//val gsonWithDate: Gson
//    get() = GsonBuilder()
//        .setDateFormat("yyyy-MM-dd HH:mm:ss")
//        .create()
