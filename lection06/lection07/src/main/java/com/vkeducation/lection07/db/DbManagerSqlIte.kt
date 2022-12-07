package com.vkeducation.lection07.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Менеджер, управляющий базой данных. Занимается чтением/записью данных в выделенном потоке.
 * Все callback'и вызываются в потоке исполнения (не переводятся в UI-thread).
 */
class DbManagerSqlIte {
    private val executor: Executor = Executors.newSingleThreadExecutor()
    private lateinit var context: Context
    private lateinit var database: SQLiteDatabase
    fun insert(text: String) {
        executor.execute {
            insertInternal(text);
        }
    }

    fun readAll(listener: ReadAllListener<String?>) {
        executor.execute {
            readAllInternal(listener)
        }
    }

    fun clean() {
        executor.execute {
            cleanInternal()
        }
    }

    fun initialize() : SQLiteDatabase {
        if (::database.isInitialized) {
            return database
        }
        val helper: SQLiteOpenHelper =
            object : SQLiteOpenHelper(context, DB_NAME, null, VERSION + 2) {
                override fun onCreate(db: SQLiteDatabase) {
                    createDatabase(db)
                }

                override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}
            }
        database = helper.writableDatabase
        return database
    }

    private fun createDatabase(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE '$TABLE_NAME' (ID INTEGER PRIMARY KEY, $TEXT_COLUMN TEXT NOT NULL)")
    }

    private fun insertInternal(text: String) {
        initialize()
        val contentValues = ContentValues()
        contentValues.put(TEXT_COLUMN, text)
        val insert = database.insert(TABLE_NAME, null, contentValues)
        //database.execSQL("INSERT INTO $TABLE_NAME ($TEXT_COLUMN) VALUES (?)", arrayOf(text));
    }

    private fun readAllInternal(listener: ReadAllListener<String?>) {
        initialize()
        val curs = database.rawQuery("SELECT * FROM $TABLE_NAME", null)
        if (curs == null) {
            listener.onReadAll(emptyList())
            return
        }
        val result: MutableList<String> = ArrayList()
        curs.use { cursor ->
            while (cursor.moveToNext()) {
                val index = cursor.getColumnIndexOrThrow(TEXT_COLUMN)
                result.add(cursor.getString(index))
            }
        }
        listener.onReadAll(result)
    }

    private fun cleanInternal() {
        initialize()
        database.execSQL("DELETE FROM $TABLE_NAME")
    }

    interface ReadAllListener<T> {
        fun onReadAll(allItems: Collection<T>)
    }

    companion object {
        private const val VERSION = 1

        @SuppressLint("StaticFieldLeak")
        private val INSTANCE = DbManagerSqlIte()
        const val TEXT_COLUMN = "TEXT_COLUMN"
        const val TABLE_NAME = "TABLE_NAME"
        private const val DB_NAME = "MyDatabase.db"
        fun getInstance(context: Context): DbManagerSqlIte {
            INSTANCE.context = context.applicationContext
            return INSTANCE
        }
    }
}