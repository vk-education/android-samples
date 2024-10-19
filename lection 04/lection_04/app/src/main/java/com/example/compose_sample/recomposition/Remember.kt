@file:Suppress("UNUSED_PARAMETER", "unused")

package com.example.compose_sample.recomposition

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlin.math.roundToInt

@Composable
fun ScreenWithRatingBar() {
    RatingBar(
        rating = 4f,
        fillStrategy = remember { IntegerRatingBarFillStrategy() },
    )
}

@Composable
fun RatingBar(
    rating: Float,
    fillStrategy: RatingBarFillStrategy,
) {
    // draw rating bar ...
}

sealed interface RatingBarFillStrategy {
    fun calculateWidthPx(
        rating: Float,
        ratingIconWidthPx: Int,
        spacingPx: Int,
        ratingBarWidthPx: Int,
    ): Int
}

class IntegerRatingBarFillStrategy : RatingBarFillStrategy {
    override fun calculateWidthPx(
        rating: Float,
        ratingIconWidthPx: Int,
        spacingPx: Int,
        ratingBarWidthPx: Int
    ): Int {
        val stars = rating.roundToInt()
        return (stars * ratingIconWidthPx) - (spacingPx * stars - 1)
    }
}
