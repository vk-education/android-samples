package com.vkeducation.lection07.db.cp

import android.content.ContentValues
import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.vkeducation.lection07.R
import com.vkeducation.lection07.db.DbManagerSqlIte

//Что здесь НЕ так: ВСЁ(!!!) на main-thread
//Не делайте так =)
//демонстрация работы запроса по uri
class ContentProviderActivity : AppCompatActivity() {

    private val textsUri = DbUri.create(DbUri.texts)
    private val observer = object : ContentObserver(Handler(Looper.myLooper()!!)) {
        override fun onChange(selfChange: Boolean) {
            super.onChange(selfChange)
            queryAll()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sqlite)
        val editText = findViewById<EditText>(R.id.edit_text)

        findViewById<View>(R.id.add).setOnClickListener {
            insert(editText.text.toString())
            editText.setText("")
        }

        findViewById<View>(R.id.enumerate).setOnClickListener { queryAll() }
        findViewById<View>(R.id.clean).setOnClickListener { deleteAll() }

        contentResolver.registerContentObserver(textsUri, false, observer)
    }

    override fun onDestroy() {
        super.onDestroy()
        contentResolver.unregisterContentObserver(observer)
    }

    private fun queryAll() {
        val items = mutableListOf<String>()
        val cursor = contentResolver.query(
            textsUri, null, null, null, null
            //"${DbManagerSqlIte.TEXT_COLUMN} ASC"
        )
        cursor.use { c ->
            while (c != null && c.moveToNext()) {
                val text = c.getString(c.getColumnIndexOrThrow(DbManagerSqlIte.TEXT_COLUMN))
                items.add(text)
            }
        }
        showStringList(items)
    }

    private fun insert(text: String) {
        val cv = ContentValues()
        cv.put(DbManagerSqlIte.TEXT_COLUMN, text)
        contentResolver.insert(DbUri.create(DbUri.text("some id")), cv)
    }

    private fun deleteAll() {
        contentResolver.delete(textsUri, null, null)
    }

    private fun showStringList(list: Collection<String?>) {
        AlertDialog.Builder(this)
            .setItems(list.toTypedArray(), null)
            .show()
    }

}