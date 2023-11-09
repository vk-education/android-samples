package com.example.lection02.ui

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class ResultContract() : ActivityResultContract<String, String>() {

    override fun createIntent(context: Context, input: String): Intent {
        return Intent(context, SecondActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String {
        return intent?.getStringExtra("result").orEmpty()
    }
}