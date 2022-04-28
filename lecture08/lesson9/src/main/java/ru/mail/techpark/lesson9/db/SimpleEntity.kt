package ru.mail.techpark.lesson9.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "simple_entity")
class SimpleEntity {
    @PrimaryKey(autoGenerate = true)
    var id = 0
    var text: String? = null
}