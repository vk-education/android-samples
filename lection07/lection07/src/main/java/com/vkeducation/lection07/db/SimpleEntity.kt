package com.vkeducation.lection07.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "simple_entity")
class SimpleEntity {
    @PrimaryKey(autoGenerate = true)
    var id = 0
    var text: String? = null
}