package ru.mail.techpark.lesson9.db

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
internal class DbManager {
    private val executor: Executor = Executors.newSingleThreadExecutor()
    private var context: Context? = null
    private var database: SQLiteDatabase? = null
    fun insert(text: String) {
        executor.execute {
//             insertInternal(text);
            insertRoom(text)
        }
    }

    fun readAll(listener: ReadAllListener<String?>) {
        executor.execute {
            readAllRoom(listener)
//                            readAllInternal(listener);
        }
    }

    fun clean() {
        executor.execute {
//                            cleanInternal();
            cleanRoom()
        }
    }

    private fun cleanRoom() {
        val dao: SimpleEntityDao = AppDatabase.getInstance(context)!!.dao
        dao.delete(*dao.allEntities.toTypedArray())
    }

    private fun checkInitialized() {
        if (database != null) {
            return
        }
        val helper: SQLiteOpenHelper =
            object : SQLiteOpenHelper(context, DB_NAME, null, VERSION + 2) {
                override fun onCreate(db: SQLiteDatabase) {
                    createDatabase(db)
                }

                override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}
            }
        database = helper.writableDatabase
    }

    private fun createDatabase(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE '" + TABLE_NAME + "' (ID INTEGER PRIMARY KEY, " + TEXT_COLUMN + " TEXT NOT NULL)")
    }

    private fun insertInternal(text: String) {
        checkInitialized()
        val contentValues = ContentValues()
        contentValues.put(TEXT_COLUMN, text)
//        val insert = database!!.insert(TABLE_NAME, null, contentValues)
                database!!.execSQL("INSERT INTO $TABLE_NAME ($TEXT_COLUMN) VALUES (?)", arrayOf(text))
    }

    private fun insertRoom(text: String) {
        val simpleEntity = SimpleEntity()
        simpleEntity.text = text
        AppDatabase.getInstance(context)!!.dao.insertAll(simpleEntity)
    }

    private fun readAllRoom(listener: ReadAllListener<String?>) {
        val list: List<SimpleEntity> =
            AppDatabase.getInstance(context)!!.dao.allEntities
        val strings = ArrayList<String?>()
        for (simpleEntity in list) {
            strings.add(simpleEntity.text)
        }
        listener.onReadAll(strings)
    }

    private fun readAllInternal(listener: ReadAllListener<String?>) {
        checkInitialized()
        val cursor = database!!.rawQuery("SELECT * FROM $TABLE_NAME", null)
        if (cursor == null) {
            listener.onReadAll(emptyList())
            return
        }
        val result: MutableList<String> = ArrayList()
        cursor.use { cursor ->
            while (cursor.moveToNext()) {
                result.add(cursor.getString(cursor.getColumnIndex(TEXT_COLUMN)))
            }
        }
        listener.onReadAll(result)
    }

    private fun cleanInternal() {
        checkInitialized()
        database!!.execSQL("DELETE FROM $TABLE_NAME")
    }

    interface ReadAllListener<T> {
        fun onReadAll(allItems: Collection<T>)
    }

    companion object {
        private const val VERSION = 1

        @SuppressLint("StaticFieldLeak")
        private val INSTANCE = DbManager()
        private const val TEXT_COLUMN = "TEXT_COLUMN"
        private const val TABLE_NAME = "TABLE_NAME"
        private const val DB_NAME = "MyDatabase.db"
        fun getInstance(context: Context): DbManager {
            INSTANCE.context = context.applicationContext
            return INSTANCE
        }
    }
}