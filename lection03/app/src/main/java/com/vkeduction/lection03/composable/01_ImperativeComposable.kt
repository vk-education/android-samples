package com.vkeduction.lection03.composable

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun ImperativeComposable(isActive: Boolean) {
    Box {
      if (isActive) {
          Text("Лампочка горит!")
      } else Text("Лампочка не горит!")
    }
}