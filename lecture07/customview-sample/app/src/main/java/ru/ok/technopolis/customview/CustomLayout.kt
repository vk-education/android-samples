package ru.ok.technopolis.customview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.FrameLayout
import androidx.annotation.AttrRes

class CustomLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var isVertical = false;

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomLayout)
        isVertical = typedArray.getBoolean(R.styleable.CustomLayout_isVertical, false)
    }

    fun setOrientation(vertical: Boolean) {
        isVertical = vertical
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        Log.d("!!!", "onMeasure")

        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)

        val widthSpec = MeasureSpec.makeMeasureSpec(width / childCount, MeasureSpec.EXACTLY)
        for (i in 0 until childCount) {
            getChildAt(i).measure(widthSpec, heightMeasureSpec)
        }

        setMeasuredDimension(width, height)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        Log.d("!!!", "onLayout")
        var currentLeft = 0
        for (i in 0 until childCount) {
            getChildAt(i).layout(
                currentLeft,
                0,
                currentLeft + measuredWidth / childCount,
                measuredHeight
            )
            currentLeft += measuredWidth / childCount
        }
    }
}