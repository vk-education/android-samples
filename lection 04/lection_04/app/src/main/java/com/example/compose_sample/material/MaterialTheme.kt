@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.compose_sample.material

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose_sample.AppDimens
import com.example.compose_sample.R

@Composable
fun MaterialScreen() {
    MaterialTheme(
        // Это похоже на тот код, что студия генерирует при создании проекта
        // с экраном на Compose. Однако там дополнительно поддержана
        // динамическая тема.
        colorScheme = if (isSystemInDarkTheme()) {
            darkColorScheme()
        } else {
            lightColorScheme()
        }
    ) {
        // Scaffold в Material Theme – базовый контейнер для любого экрана
        // Содержит слоты для топ бара (еще его называют тулбар), боттом бара
        // (для нижнего меню навигации), снакбара (всплывающее уведомление
        // снизу) и FAB (floating action button, кнопка действия в правом
        // нижнем углу).
        Scaffold(
            topBar = {
                MaterialScreenTopBar(
                    onClickBack = { },
                )
            },
            floatingActionButton = {
                MaterialScreenMailFab(
                    onClick = { },
                )
            },
            snackbarHost = {
                Snackbar(
                    modifier = Modifier
                        .padding(horizontal = AppDimens.BaseHorizontalPadding),
                ) {
                    Text(
                        text = "Snackbar text",
                    )
                }
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = "Content")
            }
        }
    }
}

@Composable
private fun MaterialScreenTopBar(
    onClickBack: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                text = "Material Screen",
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onClickBack,
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_arrow_back_24),
                    contentDescription = stringResource(R.string.material_design_sample_back),
                )
            }
        },
    )
}

@Composable
private fun MaterialScreenMailFab(
    onClick: () -> Unit,
) {
    FloatingActionButton(
        onClick = onClick,
    ) {
        Icon(
            painter = painterResource(R.drawable.round_mail_24),
            contentDescription = stringResource(R.string.mail),
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun MaterialScreenPreview() {
    MaterialScreen()
}

@Preview(showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun MaterialScreenDarkModePreview() {
    MaterialScreen()
}
