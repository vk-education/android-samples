package com.vkeducation.lection07.db

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.vkeducation.lection07.R
import com.vkeducation.lection07.db.DbManagerRoom.ReadAllListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

/**
 * Activity для демонстрации примера работы с SQLite при помощи выделенного
 * менеджера (см. [DbManagerRoom])
 */
class RoomActivity : AppCompatActivity() {

    private lateinit var manager: DbManagerRoom

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        manager = DbManagerRoom.getInstance(this)
        setContentView(R.layout.activity_sqlite)
        val editText = findViewById<EditText>(R.id.edit_text)

        findViewById<View>(R.id.add).setOnClickListener {
            manager.insert(editText.text.toString())
            editText.setText("")
        }

        findViewById<View>(R.id.enumerate).setOnClickListener { readAll() }
        findViewById<View>(R.id.clean).setOnClickListener { manager.clean() }
    }

    private fun readAll() {
        lifecycleScope.launch(Job() + Dispatchers.Main) {
            manager.queryAll()
                .flowOn(Dispatchers.IO)
                .collect { showStringList(it) }
        }
    }

    private fun showStringList(list: Collection<String?>) {
        AlertDialog.Builder(this)
            .setItems(list.toTypedArray(), null)
            .show()
    }
}