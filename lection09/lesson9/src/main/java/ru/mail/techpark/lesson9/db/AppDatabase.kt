package ru.mail.techpark.lesson9.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Person::class, SimpleEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val personDao: PersonDao?
    abstract val dao: SimpleEntityDao

    companion object {
        private var instance: AppDatabase? = null
        @Synchronized
        fun getInstance(context: Context?): AppDatabase? {
            if (instance == null) {
                instance = create(context)
            }
            return instance
        }

        private fun create(context: Context?): AppDatabase {
            return Room.databaseBuilder(
                context!!,
                AppDatabase::class.java,
                "my_db_name"
            )
                .allowMainThreadQueries()
                .build()
        }
    }
}