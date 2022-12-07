package com.vkeducation.lection07.db.cp

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import com.vkeducation.lection07.db.DbManagerSqlIte

class DbContentProvider : ContentProvider() {

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
    //@Inject
    private lateinit var database : SQLiteDatabase

    override fun getType(uri: Uri): String? {
        //"Implement this to handle requests for the MIME type of the data at the given URI"
        return null
    }

    override fun onCreate(): Boolean {
        database = DbManagerSqlIte.getInstance(context!!).initialize()
        //make uri matcher work
        uriMatcher.addURI(DbUri.AUTHORITY, DbUri.ids, IDS.IDS)
        uriMatcher.addURI(DbUri.AUTHORITY, DbUri.texts, IDS.TEXTS)
        uriMatcher.addURI(DbUri.AUTHORITY, DbUri.text("*"), IDS.TEXT)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        val cursor = when (uriMatcher.match(uri)) {
            IDS.IDS -> {
                val tableName = DbManagerSqlIte.TABLE_NAME
                val builder = SQLiteQueryBuilder()
                builder.appendWhere("ID = 104")
                builder.tables = tableName
                builder.query(database, projection, selection, selectionArgs, null, null, sortOrder)
            }
            IDS.TEXTS -> database.query(DbManagerSqlIte.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder)
            IDS.TEXT -> database.query(DbManagerSqlIte.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder)
            else -> null
        }
        return cursor
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri {
        val contentResolver = context!!.contentResolver
        when (uriMatcher.match(uri)) {
            IDS.IDS -> {} //do nothing, i.e. we don't insert ids separately
            IDS.TEXTS -> {
                if (values != null)
                    batchInsertInSingleTransaction(database, DbManagerSqlIte.TABLE_NAME, arrayOf(values))
            }
            IDS.TEXT -> {
                database.insertWithOnConflict(DbManagerSqlIte.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE)
                //also notifying all texts uri
                contentResolver.notifyChange(DbUri.create(DbUri.texts), null)
            }
        }
        //notify of changing
        contentResolver.notifyChange(uri, null)
        return uri
    }

    //bulk insert also supported
    override fun bulkInsert(uri: Uri, values: Array<out ContentValues>): Int {
        return super.bulkInsert(uri, values)
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        where: String?,
        whereClause: Array<String>?
    ): Int {
        val count: Int = when (uriMatcher.match(uri)) {
            IDS.IDS -> database.update(DbManagerSqlIte.TABLE_NAME, values, where, whereClause)
            IDS.TEXTS -> database.update(DbManagerSqlIte.TABLE_NAME, values, where, whereClause)
            IDS.TEXT -> database.update(DbManagerSqlIte.TABLE_NAME, values, where, whereClause)
            else -> 0
        }
        //notify of changing
        if (count > 0)
            context!!.contentResolver.notifyChange(uri, null)

        return count
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val count: Int = when (uriMatcher.match(uri)) {
            IDS.IDS -> database.delete(DbManagerSqlIte.TABLE_NAME, selection, selectionArgs)
            IDS.TEXTS -> database.delete(DbManagerSqlIte.TABLE_NAME, selection, selectionArgs)
            IDS.TEXT -> database.delete(DbManagerSqlIte.TABLE_NAME, selection, selectionArgs)
            else -> 0
        }
        //notify of changing
        if (count > 0)
            context!!.contentResolver.notifyChange(uri, null)
        return count
    }

    private fun batchInsertInSingleTransaction(
        database: SQLiteDatabase,
        table: String,
        valuesIn: Array<ContentValues>
    ): Int {
        var count = 0
        database.beginTransaction()
        for (contentValues in valuesIn) {
            val inserted: Long =
                database.insertWithOnConflict(table, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE)
            if (inserted != -1L) {
                count++
            }
        }
        database.setTransactionSuccessful()
        database.endTransaction()
        return count
    }

    companion object IDS {
        const val IDS = 0

        const val TEXTS = 101
        const val TEXT = 102
    }
}