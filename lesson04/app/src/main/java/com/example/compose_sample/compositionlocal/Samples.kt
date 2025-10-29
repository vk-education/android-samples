@file:Suppress("unused")

package com.example.compose_sample.compositionlocal

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager

@Composable
fun UsefulCompositionLocals() {
    LocalContext.current
    LocalConfiguration.current
    LocalFocusManager.current
}
