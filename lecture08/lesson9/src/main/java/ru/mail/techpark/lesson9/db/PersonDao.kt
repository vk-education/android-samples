package ru.mail.techpark.lesson9.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PersonDao {
    // Добавление Person в бд
    @Insert
    fun insertAll(vararg people: Person?)

    // Удаление Person из бд
    @Delete
    fun delete(person: Person?)

    // Получение всех Person из бд
    @get:Query("SELECT * FROM person")
    val allPeople: List<Person?>?

    // Получение всех Person из бд с условием
    @Query("SELECT * FROM person WHERE favoriteColor LIKE :color")
    fun getAllPeopleWithFavoriteColor(color: String?): List<Person?>?
}