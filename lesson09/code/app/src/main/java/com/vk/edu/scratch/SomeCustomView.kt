package com.vk.edu.scratch

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.View
import java.util.Date
import kotlin.math.min

class SomeCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : View(context, attrs, defStyleAttr, defStyleRes) {

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Log.d(TAG, "I'm in a parent hierarchy")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.d(TAG, "Parent wants to know my size")

        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = MeasureSpec.getSize(widthMeasureSpec)
        val myWishfulWidth = calculateMyViewWidth()

        val width = when (widthMode) {
            MeasureSpec.EXACTLY -> {
                widthSize
            }
            MeasureSpec.AT_MOST -> {
                min(myWishfulWidth, widthSize)
            }
            else -> {
                myWishfulWidth
            }
        }

        val height = resolveSize(calculateMyViewHeight(), heightMeasureSpec)

        setMeasuredDimension(width, height)
    }

    private fun calculateMyViewHeight(): Int {
        return 10
    }

    private fun calculateMyViewWidth(): Int {
        return 10
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        Log.d(TAG, "Draw time")
        val bad = Date()
    }

    companion object {
        private const val TAG = "L08"
    }
}

