package com.example.compose_sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.ui.platform.ComposeView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // sample with compose view in android view
        setContentView(R.layout.activity_main)
        initComposeView()
        /*
        // sample with compose hello world
        setContent {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "Hello, world!",
                )
            }
        }
         */
    }

    private fun initComposeView() {
        findViewById<ComposeView>(R.id.compose_view)
            .setContent {
                Text("This is compose text")
            }
    }
}
