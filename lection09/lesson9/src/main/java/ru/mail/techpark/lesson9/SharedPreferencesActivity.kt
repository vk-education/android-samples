package ru.mail.techpark.lesson9

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
    private var mCheckBox: CheckBox? = null
    private var mEditString: EditText? = null
    private var mSeekBar: SeekBar? = null
    private var mEditFloat: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared_preferences)
        mCheckBox = findViewById(R.id.checkbox)
        mEditString = findViewById(R.id.edit_text)
        mSeekBar = findViewById(R.id.seek)
        mEditFloat = findViewById(R.id.edit_text_float)
    }

    override fun onStart() {
        super.onStart()
//        val prefs = getSharedPreferences(MODE_PRIVATE)
        val prefs = getSharedPreferences("file_name", MODE_PRIVATE)
        mCheckBox!!.isChecked = prefs.getBoolean(KEY_CHECK, false)
        mEditString!!.setText(prefs.getString(KEY_TEXT, ""))
        mSeekBar!!.progress = prefs.getInt(KEY_SEEK_PROGRESS, 50)
        val f = prefs.getString(KEY_FLOAT, "")
        mEditFloat!!.setText(f.toString())
    }

    override fun onStop() {
        super.onStop()
        val editor = getSharedPreferences("file_name", MODE_PRIVATE).edit()
        editor.putBoolean(KEY_CHECK, mCheckBox!!.isChecked)
        editor.putString(KEY_TEXT, mEditString!!.text.toString())
        editor.putInt(KEY_SEEK_PROGRESS, mSeekBar!!.progress)
        editor.putString(KEY_FLOAT, mEditFloat!!.text.toString())
        editor.apply()
    }

    companion object {
        private const val KEY_CHECK = "check"
        private const val KEY_TEXT = "text"
        private const val KEY_SEEK_PROGRESS = "seek_progress"
        private const val KEY_FLOAT = "float_value"
    }
}