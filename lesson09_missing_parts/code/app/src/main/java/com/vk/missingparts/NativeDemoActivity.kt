package com.vk.missingparts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.vk.missingparts.databinding.ActivityNativeDemoBinding

class NativeDemoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNativeDemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNativeDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentPoint = Point(-1, 1)
//        binding.sampleText.text = calculateNextPoint(currentPoint).toString()
        calculateNextPoint(currentPoint, true).toString()
    }

    external fun calculateNextPoint(point: Point, show: Boolean): Point

    fun showPoint(point: Point) {
        binding.sampleText.text = point.toString()
    }

    companion object {
        init {
            System.loadLibrary("vkedunative")
        }
    }
}

data class Point(val x: Int, val y: Int)