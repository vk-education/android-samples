package ru.mail.education.lesson05

import android.os.Bundle
import android.widget.Toast

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val command = intent.data?.host
        if (command != null) {
            Toast.makeText(this, "command: $command", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}