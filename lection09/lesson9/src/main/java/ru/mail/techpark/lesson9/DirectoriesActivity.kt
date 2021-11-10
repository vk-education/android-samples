package ru.mail.techpark.lesson9

import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.File

/**
 * Activity, которая просто отображает пути до различных системных директорий.
 */
class DirectoriesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_directories)
        var path: String?

        path = filesDir.absolutePath
        (findViewById<View>(R.id.files_dir) as TextView).text = path

        path = getDir("MY_private_dir", MODE_PRIVATE).absolutePath
        (findViewById<View>(R.id.get_dir) as TextView).text = path

        path = cacheDir.absolutePath
        (findViewById<View>(R.id.cache_dir) as TextView).text =
            path

        val externalDir = getExternalFilesDir(Environment.DIRECTORY_MOVIES)
        path = if (externalDir != null) externalDir.absolutePath else "null"
        (findViewById<View>(R.id.external_files_dir) as TextView).text =
            path

        val externalCacheDir = externalCacheDir
        path = if (externalCacheDir != null) externalCacheDir.absolutePath else "null"
        (findViewById<View>(R.id.external_cache_dir) as TextView).text = path

        path =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath
        (findViewById<View>(R.id.downloads_dir) as TextView).text =
            path

        path =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).absolutePath
        (findViewById<View>(R.id.pictures_dir) as TextView).text =
            path

        Thread {
            File(externalCacheDir, "mycache").writeText("blah blah")
        }.start()
    }
}