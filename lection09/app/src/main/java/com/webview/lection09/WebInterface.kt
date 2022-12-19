package com.webview.lection09

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.webkit.JavascriptInterface
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class WebInterface(private val context: Context) {

    @JavascriptInterface
    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    @JavascriptInterface
    fun showDialog(message: String) {
        val handler = Handler(context.mainLooper)
        val runnable = Runnable {
            AlertDialog.Builder(context)
                .setMessage(message)
                .show()
        }
        handler.post(runnable)
    }
}