package ru.mail.techpark.lesson9.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Person {
    @PrimaryKey
    var name: String = ""
    var age = 0
    var favoriteColor: String? = null
}