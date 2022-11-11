package com.vkeducation.lection07

import android.os.Bundle
import android.widget.CheckBox
import android.widget.EditText
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity

/**
 * Activity для демонстрации работы с [SharedPreferences]. Показывает чтение и запись
 * различных типов данных.
 */
class SharedPreferencesActivity : AppCompatActivity() {
    private lateinit var checkBox: CheckBox
    private lateinit var editString: EditText
    private lateinit var seekBar: SeekBar
    private lateinit var editFloat: EditText
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared_preferences)
        checkBox = findViewById(R.id.checkbox)
        editString = findViewById(R.id.edit_text)
        seekBar = findViewById(R.id.seek)
        editFloat = findViewById(R.id.edit_text_float)
    }

    override fun onStart() {
        super.onStart()
        val prefs = getSharedPreferences("file_name", MODE_PRIVATE)
        checkBox.isChecked = prefs.getBoolean(KEY_CHECK, false)
        editString.setText(prefs.getString(KEY_TEXT, ""))
        seekBar.progress = prefs.getInt(KEY_SEEK_PROGRESS, 50)
        val f = prefs.getString(KEY_FLOAT, "")
        editFloat.setText(f.toString())
    }

    override fun onStop() {
        super.onStop()
        val editor = getSharedPreferences("file_name", MODE_PRIVATE).edit()
        editor.putBoolean(KEY_CHECK, checkBox.isChecked)
        editor.putString(KEY_TEXT, editString.text.toString())
        editor.putInt(KEY_SEEK_PROGRESS, seekBar.progress)
        editor.putString(KEY_FLOAT, editFloat.text.toString())
        editor.apply()
    }

    companion object {
        private const val KEY_CHECK = "check"
        private const val KEY_TEXT = "text"
        private const val KEY_SEEK_PROGRESS = "seek_progress"
        private const val KEY_FLOAT = "float_value"
    }
}