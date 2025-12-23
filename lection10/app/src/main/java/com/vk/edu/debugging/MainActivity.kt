package com.vk.edu.debugging

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vk.edu.debugging.demo.EventProducer
import com.vk.edu.debugging.demo.ListenerImpl
import com.vk.edu.debugging.ui.theme.VkEduLectionDebuggingTheme
import java.util.Random
import kotlin.time.measureTime

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()

    init {
        EventProducer.registerListener(ListenerImpl(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val state by viewModel.state.collectAsState()
            VkEduLectionDebuggingTheme {
                JokeScreen(
                    state,
                    onRefreshClick = { viewModel.loadJoke() }
                )
            }
        }
    }
}

@Composable
private fun JokeScreen(
    state: JokeState,
    onRefreshClick: () -> Unit = {},
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            AddJokeFab(onClick = onRefreshClick)
        },
    ) { innerPadding ->
        JokeTextContainer(state, innerPadding)
    }
}

@Composable
fun AddJokeFab(onClick: () -> Unit) {
    FloatingActionButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.Refresh,
            contentDescription = stringResource(R.string.add_joke_content_description),
        )
    }
}

@Composable
fun JokeTextContainer(
    jokeState: JokeState,
    innerPadding: PaddingValues
) {
    when (jokeState) {
        JokeState.Loading -> Loading()
        is JokeState.Data -> Joke(
            jokeText = jokeState.jokeText,
            url = jokeState.jokeUrl,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun Loading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(modifier = Modifier.size(64.dp))
    }
}

@Composable
fun Joke(
    jokeText: String,
    url: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = jokeText,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 64.dp)
        )
        Text(
            text = url,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    VkEduLectionDebuggingTheme {
        JokeScreen(
            JokeState.Data(
                jokeText = "Kolobok hanged himself out",
                jokeUrl = "https://e.mail.ru/",
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingPreview() {
    VkEduLectionDebuggingTheme {
        JokeScreen(JokeState.Loading)
    }
}



