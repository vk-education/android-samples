package company.vk.lesson07.datalayer.sql

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import company.vk.lesson07.ContractPlates
import company.vk.lesson07.objects.Plate

class PlateSqlHelper(context: Context) : SQLiteOpenHelper(context, "SqlAccessor", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        if (db == null) {
            return
        }

        db.execSQL(CREATE_PLATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (db == null) {
            return
        }

        db.execSQL(DROP_PLATE_TABLE)
        onCreate(db)
    }

    fun plateCount(): Long {
        return readableDatabase.use { DatabaseUtils.queryNumEntries(it, TABLE_PLATES) }
    }

    @SuppressLint("Range")
    fun plates(): List<Plate> {
        return readableDatabase.use { db ->
            db.rawQuery(SELECT_PLATE_ALL, null).use { cursor -> cursor.convert() }
        }
    }

    fun insertPlate(value: String, color: Int): Long {
        val values = ContentValues().apply {
            put(ContractPlates.COLUMN_VALUE, value)
            put(ContractPlates.COLUMN_COLOR, color)
        }

        return writableDatabase.use { it.insert(TABLE_PLATES, null, values) }
    }

    fun clear(): Int {
        return writableDatabase.use { it.delete(TABLE_PLATES, null, null) }
    }

    protected fun Cursor?.convert(): List<Plate> {
        if (this == null || !moveToFirst()) {
            return emptyList()
        }

        val list = mutableListOf<Plate>()

        val valueColumnIndex = getColumnIndex(ContractPlates.COLUMN_VALUE)
        val colorColumnIndex = getColumnIndex(ContractPlates.COLUMN_COLOR)

        while (!isAfterLast) {
            val plate = Plate(getString(valueColumnIndex), getInt(colorColumnIndex))
            list.add(plate)

            moveToNext()
        }

        return list
    }

    companion object {
        protected const val TABLE_PLATES = "PLATES"

        protected const val CREATE_PLATE_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_PLATES (${ContractPlates.COLUMN_VALUE} TEXT NOT NULL, ${ContractPlates.COLUMN_COLOR} INTEGER NOT NULL);"
        protected const val DROP_PLATE_TABLE = "DROP TABLE IF EXISTS $TABLE_PLATES"
        protected const val SELECT_PLATE_ALL = "SELECT * FROM $TABLE_PLATES"
    }
}