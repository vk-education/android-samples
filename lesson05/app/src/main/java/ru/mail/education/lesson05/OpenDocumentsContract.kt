package ru.mail.education.lesson05

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import java.util.*

class OpenDocumentsContract: ActivityResultContract<Array<String>?, List<Uri>>() {
    override fun createIntent(context: Context, input: Array<String>?): Intent {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            .putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            .setType("*/*")


        if (!input.isNullOrEmpty()) {
            intent.putExtra(Intent.EXTRA_MIME_TYPES, input)
        }

        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): List<Uri> {
        return if (resultCode != Activity.RESULT_OK || intent == null) emptyList() else getClipDataUris(intent)
    }

    override fun getSynchronousResult(context: Context, input: Array<String>?): SynchronousResult<List<Uri>>? {
        return null
    }


    fun getClipDataUris(intent: Intent): List<Uri> {
        // Use a LinkedHashSet to maintain any ordering that may be
        // present in the ClipData
        val resultSet = LinkedHashSet<Uri>()
        intent.data?.let { resultSet.add(it) }

        val clipData = intent.clipData
        if (clipData == null && resultSet.isEmpty()) {
            return emptyList()
        } else if (clipData != null) {
            for (i in 0 until clipData.itemCount) {
                val uri = clipData.getItemAt(i).uri
                if (uri != null) {
                    resultSet.add(uri)
                }
            }
        }
        return ArrayList(resultSet)
    }
}