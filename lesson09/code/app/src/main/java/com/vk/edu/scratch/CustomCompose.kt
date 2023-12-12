import android.graphics.Rect
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.TransformableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@Composable
fun MarketChart(candles: List<Candle>) {
    val state =
        rememberSaveable(saver = MarketChartState.Saver) { MarketChartState.getState(candles) }

    val decimalFormat = DecimalFormat("##.00")
    val timeFormatter = DateTimeFormatter.ofPattern("dd.MM, HH:mm")
    val bounds = Rect()
    val textPaint = Paint().asFrameworkPaint().apply {
        isAntiAlias = true
        textSize = 35.sp.value
        color = Color.White.toArgb()
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val chartWidth = constraints.maxWidth - 128.dp.value
        val chartHeight = constraints.maxHeight - 64.dp.value

        state.setViewSize(chartWidth, chartHeight)
        state.calculateGridWidth()

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF182028))
                .scrollable(state.scrollableState, Orientation.Horizontal)
                .transformable(state.transformableState)
        ) {
            drawLine(
                color = Color.White,
                strokeWidth = 2.dp.value,
                start = Offset(0f, chartHeight),
                end = Offset(chartWidth, chartHeight)
            )

            drawLine(
                color = Color.White,
                strokeWidth = 2.dp.value,
                start = Offset(chartWidth, 0f),
                end = Offset(chartWidth, chartHeight)
            )

            state.timeLines.forEach { candle ->
                val offset = state.xOffset(candle)
                if (offset !in 0f..chartWidth) return@forEach
                drawLine(
                    color = Color.White,
                    strokeWidth = 1.dp.value,
                    start = Offset(offset, 0f),
                    end = Offset(offset, chartHeight),
                    pathEffect = PathEffect.dashPathEffect(
                        intervals = floatArrayOf(10f, 20f),
                        phase = 5f
                    )
                )
                drawIntoCanvas {
                    val text = candle.time.format(timeFormatter)
                    textPaint.getTextBounds(text, 0, text.length, bounds)
                    val textHeight = bounds.height()
                    val textWidth = bounds.width()
                    it.nativeCanvas.drawText(
                        text,
                        offset - textWidth / 2,
                        chartHeight + 8.dp.value + textHeight,
                        textPaint
                    )
                }
            }

            state.priceLines.forEach { value: Float ->
                val yOffset = state.yOffset(value)
                val text = decimalFormat.format(value)
                drawLine(
                    color = Color.White,
                    strokeWidth = 1.dp.value,
                    start = Offset(0f, yOffset),
                    end = Offset(chartWidth, yOffset),
                    pathEffect = PathEffect.dashPathEffect(
                        intervals = floatArrayOf(10f, 20f),
                        phase = 5f
                    )
                )
                drawIntoCanvas {
                    textPaint.getTextBounds(text, 0, text.length, bounds)
                    val textHeight = bounds.height()
                    it.nativeCanvas.drawText(
                        text,
                        chartWidth + 8.dp.value,
                        yOffset + textHeight / 2,
                        textPaint
                    )
                }
            }

            state.visibleCandles.forEach { candle ->
                val xOffset = state.xOffset(candle)
                drawLine(
                    color = Color.White,
                    strokeWidth = 2.dp.value,
                    start = Offset(xOffset, state.yOffset(candle.low)),
                    end = Offset(xOffset, state.yOffset(candle.high))
                )
                if (candle.open > candle.close) {
                    drawRect(
                        color = Color.Red,
                        topLeft = Offset(xOffset - 6.dp.value, state.yOffset(candle.open)),
                        size = Size(
                            12.dp.value,
                            state.yOffset(candle.close) - state.yOffset(candle.open)
                        )
                    )
                } else {
                    drawRect(
                        color = Color.Green,
                        topLeft = Offset(xOffset - 6.dp.value, state.yOffset(candle.close)),
                        size = Size(
                            12.dp.value,
                            state.yOffset(candle.open) - state.yOffset(candle.close)
                        )
                    )
                }
            }
        }
    }
}

data class Candle(
    val time: LocalDateTime,
    val open: Float,
    val close: Float,
    val high: Float,
    val low: Float,
) : Comparable<Candle> {

    override fun compareTo(other: Candle) = if (time.isBefore(other.time)) -1 else 1
}

class MarketChartState {

    private var candles = listOf<Candle>()
    private var visibleCandleCount by mutableStateOf(0)
    private var scrollOffset by mutableStateOf(0f)
    private var viewWidth = 0f
    private var viewHeight = 0f
    private var candleInGrid = Float.MAX_VALUE

    private val maxPrice by derivedStateOf { visibleCandles.maxOfOrNull { it.high } ?: 0f }
    private val minPrice by derivedStateOf { visibleCandles.minOfOrNull { it.low } ?: 0f }

    val transformableState = TransformableState { zoomChange, _, _ -> scaleView(zoomChange) }

    val scrollableState = ScrollableState {
        scrollOffset = if (it > 0) {
            (scrollOffset - it.scrolledCandles).coerceAtLeast(0f)
        } else {
            (scrollOffset - it.scrolledCandles).coerceAtMost(candles.lastIndex.toFloat())
        }
        it
    }

    private val Float.scrolledCandles: Float
        get() = this * visibleCandleCount.toFloat() / viewWidth

    var timeLines by mutableStateOf(listOf<Candle>())

    val priceLines by derivedStateOf {
        val priceItem = (maxPrice - minPrice) / PRICES_COUNT
        mutableListOf<Float>().apply { repeat(PRICES_COUNT) { if (it > 0) add(maxPrice - priceItem * it) } }
    }

    val visibleCandles by derivedStateOf {
        if (candles.isNotEmpty()) {
            candles.subList(
                scrollOffset.roundToInt().coerceAtLeast(0),
                (scrollOffset.roundToInt() + visibleCandleCount).coerceAtMost(candles.size)
            )
        } else {
            emptyList()
        }
    }

    private fun scaleView(zoomChange: Float) {
        if ((zoomChange < 1f && visibleCandleCount / zoomChange <= MAX_CANDLES) ||
            (zoomChange > 1f && visibleCandleCount / zoomChange >= MIN_CANDLES)
        ) {
            visibleCandleCount = (visibleCandleCount / zoomChange).roundToInt()
        }
    }

    fun setViewSize(width: Float, height: Float) {
        viewWidth = width
        viewHeight = height
    }

    fun calculateGridWidth() {
        val candleWidth = viewWidth / visibleCandleCount
        val currentGridWidth = candleInGrid * candleWidth
        when {
            currentGridWidth < MIN_GRID_WIDTH -> {
                candleInGrid = MAX_GRID_WIDTH / candleWidth
                timeLines =
                    candles.filterIndexed { index, _ -> index % candleInGrid.roundToInt() == 0 }
            }

            currentGridWidth > MAX_GRID_WIDTH -> {
                candleInGrid = MIN_GRID_WIDTH / candleWidth
                timeLines =
                    candles.filterIndexed { index, _ -> index % candleInGrid.roundToInt() == 0 }
            }
        }
    }

    fun xOffset(candle: Candle) =
        viewWidth * visibleCandles.indexOf(candle).toFloat() / visibleCandleCount.toFloat()

    fun yOffset(value: Float) = viewHeight * (maxPrice - value) / (maxPrice - minPrice)

    companion object {
        private const val MAX_GRID_WIDTH = 500
        private const val MIN_GRID_WIDTH = 250
        private const val MAX_CANDLES = 100
        private const val MIN_CANDLES = 30
        private const val START_CANDLES = 60
        private const val PRICES_COUNT = 10

        fun getState(
            candles: List<Candle>,
            visibleCandleCount: Int? = null,
            scrollOffset: Float? = null,
        ) =
            MarketChartState().apply {
                this.candles = candles
                this.visibleCandleCount = visibleCandleCount ?: START_CANDLES
                this.scrollOffset =
                    scrollOffset ?: (candles.size.toFloat() - this.visibleCandleCount)
            }

        @Suppress("UNCHECKED_CAST")
        val Saver: Saver<MarketChartState, Any> = listSaver(
            save = { listOf(it.candles, it.scrollOffset, it.visibleCandleCount) },
            restore = {
                getState(
                    candles = it[0] as List<Candle>,
                    visibleCandleCount = it[2] as Int,
                    scrollOffset = it[1] as Float
                )
            }
        )
    }
}