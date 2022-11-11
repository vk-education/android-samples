package com.vkeducation.lection07

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.vkeducation.lection07.db.RoomActivity
import com.vkeducation.lection07.db.SqliteActivity
import com.vkeducation.lection07.db.cp.ContentProviderActivity

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
        findViewById<View>(R.id.btn_data_store).setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    DataStoreActivity::class.java
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
        findViewById<View>(R.id.btn_room).setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    RoomActivity::class.java
                )
            )
        }
        findViewById<View>(R.id.btn_content_provider).setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    ContentProviderActivity::class.java
                )
            )
        }
    }
}