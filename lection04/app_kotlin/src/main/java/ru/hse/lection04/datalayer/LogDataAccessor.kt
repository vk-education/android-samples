package ru.hse.lection04.datalayer

import android.content.*
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import android.text.TextUtils
import java.util.*

/**
 * Класс для работает с логом
 */
class LogDataAccessor : ContentProvider() {
    companion object {
        const val AUTHORITY = "ru.hse.lection04.log"
        const val CONTENT_PATH = "entry"
        protected const val URL = "content://$AUTHORITY/$CONTENT_PATH"
        val CONTENT_URI = Uri.parse(URL)
        protected var PROJECTION_MAP: HashMap<String, String>? = null
        protected const val TYPE_ALL = 0
        protected const val TYPE_ITEM = 1
        protected const val MIME_TYPE_DIR = "vnd.android.cursor.dir/vnd.ru.hse.lection04.all"
        protected const val MIME_TYPE_ITEM = "vnd.android.cursor.dir/vnd.ru.hse.lection04.item"
        protected const val DATABASE_NAME = "connectivity_log.sqlite"
        protected const val TABLE_NAME = "CONNECTIVITY"
        protected const val _ID = "_id"
        const val COLUMN_DATE = "DATE"
        const val COLUMN_TYPE = "TYPE"
        protected val URI_MATCHER: UriMatcher = object : UriMatcher(NO_MATCH) {
            init {
                addURI(AUTHORITY, CONTENT_PATH, TYPE_ALL)
                addURI(AUTHORITY, "$CONTENT_PATH/#", TYPE_ITEM)
            }
        }
    }


    protected var mDatabase: SQLiteDatabase? = null


    override fun onCreate(): Boolean {
        val helper = DatabaseHelper(context)
        mDatabase = helper.writableDatabase

        if (mDatabase == null) {
            return false
        }

        // Уведоимяем, что провайдер может работать. Иначе запуск приложения будет остановлен
        return true
    }

    override fun getType(uri: Uri): String? {
        return when (URI_MATCHER.match(uri)) {
            TYPE_ALL -> MIME_TYPE_DIR
            TYPE_ITEM -> MIME_TYPE_ITEM
            else -> null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val rowID = mDatabase!!.insert(TABLE_NAME, "", values)

        if (rowID > 0) {
            val newUri = ContentUris.withAppendedId(CONTENT_URI, rowID)
            dataUpdated(newUri)
            return newUri
        }

        throw SQLException("Failed to add a record into $uri")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        var count = when (URI_MATCHER.match(uri)) {
            TYPE_ALL -> mDatabase!!.delete(TABLE_NAME, selection, selectionArgs)
            TYPE_ITEM -> {
                val id = uri.pathSegments[1]
                mDatabase!!.delete(TABLE_NAME, _ID + " = " + id + if (!TextUtils.isEmpty(selection)) " AND ($selection)" else "", selectionArgs)
            }
            else -> throw IllegalArgumentException("Unknown URI $uri")
        }
        dataUpdated(uri)
        return count
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        var count = when (URI_MATCHER.match(uri)) {
            TYPE_ALL -> mDatabase!!.update(TABLE_NAME, values, selection, selectionArgs)
            TYPE_ITEM -> mDatabase!!.update(TABLE_NAME, values, _ID + " = " + uri.pathSegments[1] + if (!TextUtils.isEmpty(selection)) " AND ($selection)" else "", selectionArgs)
            else -> throw IllegalArgumentException("Unknown URI $uri")
        }

        dataUpdated(uri)

        return count
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        var sortOrder = sortOrder
        val builder = SQLiteQueryBuilder()
        builder.tables = TABLE_NAME

        when (URI_MATCHER.match(uri)) {
            TYPE_ALL -> builder.projectionMap = PROJECTION_MAP
            TYPE_ITEM -> builder.appendWhere(_ID + "=" + uri.pathSegments[1])
            else -> throw IllegalArgumentException("Unknown URI $uri")
        }

        if (sortOrder == null || sortOrder.isEmpty()) {
            sortOrder = COLUMN_DATE
        }

        val cursor = builder.query(mDatabase, projection, selection, selectionArgs, null, null, sortOrder)
        if (context != null) {
            cursor.setNotificationUri(context!!.contentResolver, uri)
        }

        return cursor
    }

    /**
     * Уведомляе систему о том, что даныне изменились
     * @param uri адрес измененных данных
     */
    protected fun dataUpdated(uri: Uri?) {
        val context = context ?: return
        context.contentResolver.notifyChange(uri!!, null)
    }

    protected class DatabaseHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(CREATE_DB_TABLE)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
            onCreate(db)
        }

        companion object {
            const val DATABASE_VERSION = 1
            const val CREATE_DB_TABLE = " CREATE TABLE " + TABLE_NAME +
                    " (" +
                    _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_DATE + " TEXT NOT NULL, " +
                    COLUMN_TYPE + " TEXT NOT NULL" +
                    ");"
        }
    }
}