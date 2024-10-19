package com.example.compose_sample.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun App() {
    var screen by remember { mutableStateOf("home") }

    when (screen) {
        "home" -> {
            HomeScreen(
                goToSettings = {
                    screen = "settings"
                },
            )
        }

        "settings" -> {
            SettingsScreen()
            BackHandler {
                screen = "home"
            }
        }
    }
}

@Composable
fun HomeScreen(goToSettings: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Home")
        Button(
            onClick = goToSettings
        ) {
            Text(text = "Settings")
        }
    }
}

@Composable
fun SettingsScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text("Settings")
    }
}
