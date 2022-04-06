package com.example.lecture06

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log

class SampleThread : Thread() {

    var handler: Handler? = null

    override fun run() {
        Looper.prepare()

        handler = object : Handler(Looper.myLooper()!!) {
            override fun handleMessage(msg: Message) {
                when (msg.what) {
                    OPERATION_ONE -> {
                        looper.quitSafely()
                    }
                    OPERATION_TWO -> {
                        // ignore
                    }

                }
            }
        }

        Looper.loop()

        Log.d("SampleThread", "quit from loop")
    }

    companion object {
        const val OPERATION_ONE = 1
        const val OPERATION_TWO = 2
    }
}

