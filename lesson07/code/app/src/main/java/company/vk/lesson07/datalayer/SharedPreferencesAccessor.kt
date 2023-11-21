package company.vk.lesson07.datalayer

import android.content.Context
import android.graphics.Color
import androidx.core.content.edit
import company.vk.lesson07.objects.Plate

class SharedPreferencesAccessor(context: Context): IPlateAccessor {
    protected val preferences = context.getSharedPreferences("SharedPreferencesAccessor", Context.MODE_PRIVATE)

    override fun plates(): List<Plate> {
        val count = preferences.getInt(KEY_COUNT, 0)
        if (count == 0) {
            return emptyList()
        }

        val list = mutableListOf<Plate>()
        for (index in 1 .. count) {
            val plate = Plate(
                preferences.getString(KEY_VALUE_PATTERN.format(index), "NONE")!!,
                preferences.getInt(KEY_COLOR_PATTERN.format(index), Color.BLACK)
            )

            list.add(plate)
        }

        return list
    }

    override fun createPlate(color: Int): Plate {
        val index = preferences.getInt(KEY_COUNT, 0) + 1
        val plate = Plate(index.toString(), color)

        preferences.edit(true) {
            putInt(KEY_COUNT, index)
            putString(KEY_VALUE_PATTERN.format(index), plate.value)
            putInt(KEY_COLOR_PATTERN.format(index), plate.color)
        }

        return plate
    }

    override fun clearPlates(): Boolean {
        preferences.edit {
            clear()
        }

        return preferences.getInt(KEY_COUNT, 0) == 0
    }

    companion object {
        protected const val KEY_COUNT = "KEY_COUNT"
        protected const val KEY_VALUE_PATTERN = "KEY_VALUE_PATTERN(%s)"
        protected const val KEY_COLOR_PATTERN = "KEY_COLOR_PATTERN(%s)"
    }
}