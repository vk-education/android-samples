package ru.mail.techpark.lesson9.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SimpleEntityDao {
    @Insert
    fun insertAll(vararg entities: SimpleEntity?)

    // Получение всех SimpleEntity из бд
    @get:Query("SELECT * FROM simple_entity")
    val allEntities: List<SimpleEntity>

    @Delete
    fun delete(vararg entities: SimpleEntity?)
}