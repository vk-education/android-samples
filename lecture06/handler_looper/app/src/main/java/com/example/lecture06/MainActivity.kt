package com.example.lecture06

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import com.example.lecture06.SampleThread.Companion.OPERATION_ONE
import com.example.lecture06.SampleThread.Companion.OPERATION_TWO

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val sampleThread = SampleThread()
//        sampleThread.start()
//
//        val handler = sampleThread.handler
//        handler?.sendMessage(handler.obtainMessage(OPERATION_ONE))


        val thread = HandlerThread("SampleThread")
        thread.start()

        val handler = object : Handler(thread.looper) {
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
        handler.sendMessage(handler.obtainMessage(OPERATION_ONE))
    }
}