package com.mail.courses.android.coroutines

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class MainFragment : Fragment(R.layout.main_fragment) {

    private lateinit var btnStart: Button
    private lateinit var txtRemainingTime: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txtRemainingTime = view.findViewById(R.id.txt_remaining_time)

        btnStart = view.findViewById(R.id.btn_start)
        btnStart.setOnClickListener {
            logThreadInfo("button callback")
            btnStart.isEnabled = false
            executeBenchmark()
            btnStart.isEnabled = true
        }
    }

    private fun executeBenchmark() {
        val benchmarkDurationSeconds = 5

        updateRemainingTime(benchmarkDurationSeconds)

        logThreadInfo("benchmark started")

        val stopTimeNano = System.nanoTime() + benchmarkDurationSeconds * 1_000_000_000L

        var iterationsCount: Long = 0
        while (System.nanoTime() < stopTimeNano) {
            iterationsCount++
        }

        logThreadInfo("benchmark completed")

        Toast.makeText(requireContext(), "$iterationsCount", Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("SetTextI18n")
    private fun updateRemainingTime(remainingTimeSeconds: Int) {
        logThreadInfo("updateRemainingTime: $remainingTimeSeconds seconds")

        if (remainingTimeSeconds > 0) {
            txtRemainingTime.text = "$remainingTimeSeconds seconds remaining"
            Handler(Looper.getMainLooper()).postDelayed({
                updateRemainingTime(remainingTimeSeconds - 1)
            }, 1000)
        } else {
            txtRemainingTime.text = "done!"
        }
    }

    private fun logThreadInfo(msg: String) {
        Log.i(TAG, "$msg; thread name: ${Thread.currentThread().name}; thread ID: ${Thread.currentThread().id}")
    }

    companion object {
        const val TAG = "MainFragment"
    }
}