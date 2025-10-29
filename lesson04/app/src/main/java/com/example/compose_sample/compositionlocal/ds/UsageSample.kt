package com.example.compose_sample.compositionlocal.ds

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AppSample() {
    CompositionLocalProvider(
        LocalColorScheme provides if (isSystemInDarkTheme()) {
            AppDarkColorScheme
        } else {
            AppLightColorScheme
        }
    ) {
        SomeScreen()
    }
}

@Composable
fun SomeScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppTheme.colorScheme.background.content),
        verticalArrangement = Arrangement.spacedBy(
            space = 4.dp,
            alignment = Alignment.CenterVertically,
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Hello, world!",
            color = LocalColorScheme.current.text.primary,
            style = AppTheme.typography.display,
        )
        Text(
            text = "Welcome to test app",
            color = AppTheme.colorScheme.text.secondary,
            style = AppTheme.typography.title,
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun AppSamplePreview() {
    AppSample()
}

@Preview(showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun AppSampleDarkModePreview() {
    AppSample()
}
