package com.vk.edu.debugging.repo

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Entity(tableName = "joke")
data class JokeEntity(
    @PrimaryKey(autoGenerate = true) var uid: Int = 0,
    val joke: String,
    val url: String = "",
)


@Serializable
data class JokeDto(
    @SerialName("value") val joke: String,
    val url: String,
    @SerialName("created_at") @Serializable(with = DateSerializer::class) val created: Date,
)

fun JokeDto.mapToEntity(): JokeEntity = JokeEntity(
    joke = this.joke,
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

