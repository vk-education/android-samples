package company.vk.lesson07

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.database.Cursor
import android.database.MatrixCursor
import android.graphics.Color
import android.net.Uri
import company.vk.lesson07.datalayer.sql.PlateSqlHelper
import company.vk.lesson07.objects.Plate

// Очень базовая имплементация, продакшен реализация в разы больше
class PlateContentProvider: ContentProvider() {
    // Используется имеющаяся реализация для упрощения. В данном случае это не эффективный подход
    protected val helper by lazy { PlateSqlHelper(context!!) }

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor {
        return helper.plates().convert()
    }

    override fun getType(uri: Uri): String? {
        val matchId = ContractPlates.PLATE_URI_MATCHER.match(uri)
        return ContractPlates.PLATES[matchId]
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri {
        val value = helper.plateCount() + 1
        helper.insertPlate(value.toString(), values?.getAsInteger(ContractPlates.COLUMN_COLOR)?: Color.BLACK)

        return ContentUris.withAppendedId(ContractPlates.PLATE_CONTENT_URI, value.toLong())
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return helper.clear()
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        return -1
    }

    protected fun List<Plate>.convert(): Cursor {
        // это пример конструкции кастомного курсора
        val cursor = MatrixCursor(arrayOf(ContractPlates.COLUMN_VALUE, ContractPlates.COLUMN_COLOR))

        forEach { cursor.addRow(arrayOf(it.value, it.color)) }

        return cursor
    }
}