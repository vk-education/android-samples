@file:Suppress("UNUSED_PARAMETER", "unused")

package com.example.compose_sample.modifier

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose_sample.R

@Composable
fun MyComposable(
    // Modifier should be with default value
    modifier: Modifier = Modifier,
) {
    Box(
        // `modifier` from parameters should be passed to base container or
        // component of composable.
        modifier = modifier,
    ) {
        // Content
    }
}

// Code style
@Composable
fun MyComposableWithParams(
    someRequiredParam: Int,
    // modifier should be first parameter with default value
    modifier: Modifier = Modifier,
    someParamWithDefault: Color = MaterialTheme.colorScheme.surface,
) {
    Box(
        modifier = modifier,
    ) {
        // Content
    }
}

@Composable
fun FabButtonSample(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .clickable(
                enabled = isEnabled,
                role = Role.Button,
                onClick = onClick,
            )
            .alpha(resolveAlpha(isEnabled))
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Icon(
            painter = painterResource(R.drawable.round_mail_24),
            contentDescription = stringResource(R.string.mail),
            modifier = Modifier
                .padding(all = 16.dp),
            tint = MaterialTheme.colorScheme.primary,
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ScreenWithFabButtonSamplePreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        FabButtonSample(
            onClick = {},
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(
                    end = 16.dp,
                    bottom = 16.dp,
                ),
            isEnabled = true,
        )
    }
}

private fun resolveAlpha(isEnabled: Boolean) = if (isEnabled) 1f else 0.5f
