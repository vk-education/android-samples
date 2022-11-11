package com.vkeducation.lection07

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.EditText
import android.widget.SeekBar
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import androidx.lifecycle.lifecycleScope
import com.google.protobuf.InvalidProtocolBufferException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.io.InputStream
import java.io.OutputStream

/*
    DataStore Preferences

    val EXAMPLE_COUNTER = intPreferencesKey("example_counter")
    val exampleCounterFlow: Flow<Int> = context.dataStore.data
    .map { preferences ->
    // No type safety.
    preferences[EXAMPLE_COUNTER] ?: 0
    }
 */
/*
    Singleton for application
 */
val Context.exampleDataStore: DataStore<ProtoDataStore> by dataStore(
    fileName = "ds.pb",
    serializer = DataStoreSerializer()
)

//Data Store - Proto (Protobuf)
class DataStoreActivity : AppCompatActivity() {

    private lateinit var checkBox: CheckBox
    private lateinit var editString: EditText
    private lateinit var seekBar: SeekBar
    private lateinit var editFloat: EditText

    private lateinit var screenDataStore: DataStore<ProtoDataStore>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_store)
        checkBox = findViewById(R.id.checkbox)
        editString = findViewById(R.id.edit_text)
        seekBar = findViewById(R.id.seek)
        editFloat = findViewById(R.id.edit_text_float)
        screenDataStore = applicationContext.exampleDataStore
    }

    //just for example (in prod - use viewmodel's data)
    override fun onResume() {
        super.onResume()
        lifecycleScope.launch(Job() + Dispatchers.Main) {
            screenDataStore
                .data
                .flowOn(Dispatchers.IO)
                .collect {
                    checkBox.isChecked = it.checkBox
                    editString.setText(it.editStr)
                    editFloat.setText(it.editFloat.toString())
                    seekBar.progress = it.seekBar
                }
        }
    }

    override fun onStop() {
        super.onStop()
        val isChecked = checkBox.isChecked
        val text = editString.text.toString()
        val progress = seekBar.progress
        val num = editFloat.text.toString().toFloatOrNull() ?: 0f
        lifecycleScope.launch(Job() + Dispatchers.IO) {
            screenDataStore.updateData {
                it.toBuilder()
                    .setCheckBox(isChecked)
                    .setEditFloat(num)
                    .setEditStr(text)
                    .setSeekBar(progress)
                    .build()
            }
        }
    }
}

private class DataStoreSerializer : Serializer<ProtoDataStore> {
    override val defaultValue: ProtoDataStore = ProtoDataStore.getDefaultInstance()
    override suspend fun readFrom(input: InputStream): ProtoDataStore {
        try {
            return ProtoDataStore.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: ProtoDataStore, output: OutputStream) = t.writeTo(output)
}
