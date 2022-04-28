package ru.mail.techpark.lesson9

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import ru.mail.techpark.lesson9.db.SqliteActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.btn_shared_preferences).setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    SharedPreferencesActivity::class.java
                )
            )
        }
        findViewById<View>(R.id.btn_directories).setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    DirectoriesActivity::class.java
                )
            )
        }
        findViewById<View>(R.id.btn_file_manager).setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    FileManagerActivity::class.java
                )
            )
        }
        findViewById<View>(R.id.btn_sqlite).setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    SqliteActivity::class.java
                )
            )
        }
    }
}