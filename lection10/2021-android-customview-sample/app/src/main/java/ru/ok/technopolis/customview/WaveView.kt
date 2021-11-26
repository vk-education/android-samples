package ru.ok.technopolis.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import ru.ok.technopolis.customview.LinearInterpolation.interpolateArray
import ru.ok.technopolis.customview.WaveRepository.waveData

class WaveView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {

    private val wavePath = Path()
    private val linePaint = Paint()
    private val itemWidth: Int
    private val originalData: IntArray
    private var measuredData: IntArray? = null

    companion object {
        private const val DEFAULT_ITEM_WIDTH_DP = 2
        private const val DEFAULT_ITEM_COLOR = Color.BLACK
    }

    init {
        val displayMetrics = context.resources.displayMetrics
        var itemWidthFromAttr = (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_ITEM_WIDTH_DP.toFloat(), displayMetrics) + 0.5f).toInt()
        var itemColorFromAttr = DEFAULT_ITEM_COLOR

        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.WaveView)
            itemWidthFromAttr = typedArray.getDimensionPixelSize(R.styleable.WaveView_itemWidth, itemWidthFromAttr)
            itemColorFromAttr = typedArray.getColor(R.styleable.WaveView_itemColor, itemColorFromAttr)
            typedArray.recycle()
        }

        itemWidth = itemWidthFromAttr
        originalData = waveData
        linePaint.style = Paint.Style.STROKE
        linePaint.color = itemColorFromAttr
        linePaint.strokeWidth = itemWidthFromAttr.toFloat()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)

        val itemCount = (width - paddingStart - paddingEnd + itemWidth) / (itemWidth * 2)
        measuredData = interpolateArray(originalData, itemCount)

        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        val measuredData = measuredData ?: return

        wavePath.reset()
        val measuredHeight = measuredHeight - paddingTop - paddingBottom
        var currentX = paddingStart
        for (data in measuredData) {
            val height = data.toFloat() / WaveRepository.MAX_VOLUME * measuredHeight
            val startY = measuredHeight.toFloat() / 2f - height / 2f + paddingTop
            val endY = startY + height
            wavePath.moveTo(currentX.toFloat(), startY)
            wavePath.lineTo(currentX.toFloat(), endY)
            currentX += itemWidth * 2
        }
        canvas.drawPath(wavePath, linePaint)
    }
}