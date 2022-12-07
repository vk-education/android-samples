package com.vkeducation.lection07.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SimpleEntityDao {
    @Insert
    fun insertAll(vararg entities: SimpleEntity?)

    // Получение всех SimpleEntity из бд
    @get:Query("SELECT * FROM simple_entity")
    val allEntities: List<SimpleEntity>

    @get:Query("SELECT * FROM simple_entity")
    val allEntitiesFlow: Flow<List<SimpleEntity>>

    @Delete
    fun delete(vararg entities: SimpleEntity?)
}