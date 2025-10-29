package com.example.compose_sample.composeview

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetTextI18n")
@Preview
@Composable
fun Screen() {
    Column {
        Text(text = "This is compose text")
        AndroidView(
            factory = { context ->
                TextView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        /* width = */ ViewGroup.LayoutParams.WRAP_CONTENT,
                        /* height = */ ViewGroup.LayoutParams.WRAP_CONTENT,
                    )
                    text = "This is Android View text"
                }
            },
        )
    }
}
