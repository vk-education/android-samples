package com.vk.edu.debugging.demo

import android.content.Context
import android.widget.Toast
import java.lang.ref.WeakReference

class ListenerImpl(private val context: Context) : EventListener {

    private val somePayload = Array(SIZE) { 0 }

    override fun onEvent() {
        Toast.makeText(
            context,
            "onEvent: size=${somePayload.size}",
            Toast.LENGTH_SHORT
        ).show()
    }

    companion object {
        private const val SIZE = 1
    }
}