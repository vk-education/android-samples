package com.example.compose_sample.compositionlocal.ds

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    val theme = if (isSystemInDarkTheme()) {
        AppDarkColorScheme
    } else {
        AppLightColorScheme
    }

    CompositionLocalProvider(
        LocalColorScheme provides theme
    ) {
        content()
    }
}

object AppTheme {
    val colorScheme: ColorsScheme
        @ReadOnlyComposable
        @Composable
        get() = LocalColorScheme.current

    val typography: Typography
        @ReadOnlyComposable
        @Composable
        get() = LocalTypography.current
}
