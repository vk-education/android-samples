package com.lesson.lifecycle.contract

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.lesson.lifecycle.SecondActivity
import com.lesson.lifecycle.model.InputData

class FirstToSecondActivityContract : ActivityResultContract<InputData, Int>() {

    override fun createIntent(context: Context, input: InputData): Intent {
        return Intent(context, SecondActivity::class.java).apply {
            putExtra("key", input)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Int {
        if (resultCode != Activity.RESULT_OK) return 0
        return intent?.getIntExtra("result", 0) ?: 0
    }
}