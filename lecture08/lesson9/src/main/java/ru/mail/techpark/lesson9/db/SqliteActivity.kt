package ru.mail.techpark.lesson9.db

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import ru.mail.techpark.lesson9.R
import ru.mail.techpark.lesson9.db.DbManager.ReadAllListener

/**
 * Activity для демонстрации примера работы с SQLite при помощи выделенного
 * менеджера (см. [DbManager])
 */
class SqliteActivity : AppCompatActivity() {
    private val readListener: ReadAllListener<String?> =
        object : ReadAllListener<String?> {
            override fun onReadAll(allItems: Collection<String?>) {
                runOnUiThread { showStringList(allItems) }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val manager: DbManager = DbManager.getInstance(this)
        setContentView(R.layout.activity_sqlite)
        val editText = findViewById<EditText>(R.id.edit_text)

        findViewById<View>(R.id.add).setOnClickListener {
            manager.insert(editText.text.toString())
            editText.setText("")
        }

        findViewById<View>(R.id.enumerate).setOnClickListener { manager.readAll(readListener) }
        findViewById<View>(R.id.clean).setOnClickListener { manager.clean() }
    }

    private fun showStringList(list: Collection<String?>) {
        AlertDialog.Builder(this)
            .setItems(list.toTypedArray(), null)
            .show()
    }
}