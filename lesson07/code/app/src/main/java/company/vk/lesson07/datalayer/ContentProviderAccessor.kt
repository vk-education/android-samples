package company.vk.lesson07.datalayer

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import company.vk.lesson07.ContractPlates
import company.vk.lesson07.objects.Plate

class ContentProviderAccessor(context: Context) : IPlateAccessor {
    protected val resolver = context.contentResolver

    override fun createPlate(color: Int): Plate {
        val values = ContentValues().apply {
            put(ContractPlates.COLUMN_COLOR, color)
        }

        val result = resolver.insert(ContractPlates.PLATE_CONTENT_URI, values)
        val value = result?.lastPathSegment?: "UNKNOWN"

        return Plate(value, color)
    }

    override fun plates(): List<Plate> {
        val result = resolver.query(ContractPlates.PLATE_CONTENT_URI, null, null, null, null)
        return result.use { it.convert() }
    }

    override fun clearPlates(): Boolean {
        val uri = ContentUris.withAppendedId(ContractPlates.PLATE_CONTENT_URI, ContractPlates.VALUE_ALL)
        resolver.delete(uri, null, null)

        return true
    }

    @SuppressLint("Range")
    protected fun Cursor?.convert(): List<Plate> {
        val list = mutableListOf<Plate>()

        if (this == null || !moveToFirst()) {
            return emptyList()
        }

        val valueColumnIndex = getColumnIndex(ContractPlates.COLUMN_VALUE)
        val colorColumnIndex = getColumnIndex(ContractPlates.COLUMN_COLOR)

        while (!isAfterLast) {
            val plate = Plate(getString(valueColumnIndex), getInt(colorColumnIndex))
            list.add(plate)

            moveToNext()
        }

        return list
    }
}