package com.example.compose_sample.navigation.navcompose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

val LocalNavController = staticCompositionLocalOf<NavController> {
    throw IllegalStateException("No nav controller found")
}

@Preview
@Composable
fun NavigationComposeApp() {
    val navController = rememberNavController()

    CompositionLocalProvider(
        LocalNavController provides navController
    ) {
        NavHost(
            navController = navController,
            startDestination = Route.Home.route,
        ) {
            composable(Route.Home.route) {
                HomeScreen()
            }
            composable(Route.Settings.route) {
                SettingsScreen()
            }
        }
    }
}

@Composable
fun HomeScreen() {
    val navController = LocalNavController.current

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Home")
        Button(
            onClick = {
                navController.navigate(Route.Settings.route)
            }
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

sealed class Route(val route: String) {
    data object Home : Route("home")
    data object Settings : Route("settings")
}
