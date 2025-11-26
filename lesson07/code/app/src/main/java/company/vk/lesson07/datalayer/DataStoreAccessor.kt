package company.vk.lesson07.datalayer

import android.content.Context
import android.graphics.Color
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import company.vk.lesson07.objects.Plate
import kotlinx.coroutines.runBlocking
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.first

class DataStoreAccessor(context: Context) : IPlateAccessor {

    private val Context.dataStore by preferencesDataStore(name = "DataStoreAccessor")

    private val dataStore = context.dataStore

    override fun plates(): List<Plate> = runBlocking { // !! runBlocking сделано для примера, нельзя использовать
        val preferences = dataStore.data.first()
        val count = preferences[KEY_COUNT] ?: 0
        if (count == 0) {
            return@runBlocking emptyList()
        }

        (1..count).map { index ->
            Plate(
                preferences[stringPreferencesKey(KEY_VALUE_PATTERN.format(index))] ?: "NONE",
                preferences[intPreferencesKey(KEY_COLOR_PATTERN.format(index))] ?: Color.BLACK
            )
        }
    }

    override fun createPlate(color: Int): Plate = runBlocking {
        val preferences = dataStore.data.first()
        val index = (preferences[KEY_COUNT] ?: 0) + 1
        val plate = Plate(index.toString(), color)

        dataStore.edit { prefs ->
            prefs[KEY_COUNT] = index
            prefs[stringPreferencesKey(KEY_VALUE_PATTERN.format(index))] = plate.value
            prefs[intPreferencesKey(KEY_COLOR_PATTERN.format(index))] = plate.color
        }

        return@runBlocking plate
    }

    override fun clearPlates(): Boolean = runBlocking {
        dataStore.edit { it.clear() }
        val preferences = dataStore.data.first()
        return@runBlocking (preferences[KEY_COUNT] ?: 0) == 0
    }

    companion object {
        private val KEY_COUNT = intPreferencesKey("KEY_COUNT")
        private const val KEY_VALUE_PATTERN = "KEY_VALUE_PATTERN(%s)"
        private const val KEY_COLOR_PATTERN = "KEY_COLOR_PATTERN(%s)"
    }
}
