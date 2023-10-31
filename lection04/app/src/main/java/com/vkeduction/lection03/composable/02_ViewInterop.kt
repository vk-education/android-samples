@file:Suppress("unused", "SpellCheckingInspection")

package com.vkeduction.lection03.composable

import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun ViewInterop() {
    AndroidView(
        { context ->
            TextView(context)
        }
    )
}