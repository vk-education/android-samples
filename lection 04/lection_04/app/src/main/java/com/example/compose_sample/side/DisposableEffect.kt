@file:Suppress("unused")

package com.example.compose_sample.side

import android.content.Context
import android.view.accessibility.AccessibilityManager
import android.view.accessibility.AccessibilityManager.AccessibilityStateChangeListener
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

/*
Пример: в рейтинг баре можно управлять рейтингом с помощью клика (нажав на звезду)
или с помощью горизонтального свайпа.
Клик на звезды не должен работать, если включен режим доступности talkback.

Решение: подписаться на включение режима talkback и отключать функционал клика,
если это необходимо.
 */
@Composable
fun RatingBarComponent() {
    var isTalkbackEnabled by remember { mutableStateOf(false) }

    val context = LocalContext.current
    DisposableEffect(key1 = Unit) {
        val accessibilityManager = context
            .getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager

        isTalkbackEnabled = accessibilityManager.isTouchExplorationEnabled
        val accessibilityStateChangeListener = AccessibilityStateChangeListener { enabled ->
            isTalkbackEnabled = enabled
        }
        accessibilityManager.addAccessibilityStateChangeListener(accessibilityStateChangeListener)

        onDispose {
            accessibilityManager
                .removeAccessibilityStateChangeListener(accessibilityStateChangeListener)
        }
    }

    // ...
}


@Composable
fun Test() {
    DisposableEffect(key1 = Unit) {
        // effect

        onDispose {
            // will be called on dispose
        }
    }
}
