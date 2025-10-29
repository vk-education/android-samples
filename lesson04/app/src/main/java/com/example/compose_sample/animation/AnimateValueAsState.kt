package com.example.compose_sample.animation

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TextField(
    text: String,
    onTextChanged: (String) -> Unit,
    isError: Boolean
) {
    val borderColor by animateColorAsState(
        targetValue = if (isError) {
            MaterialTheme.colorScheme.error
        } else {
            MaterialTheme.colorScheme.primary
        },
        label = "BorderAnimation",
    )

    BasicTextField(
        value = text,
        onValueChange = onTextChanged,
        decorationBox = { textField ->
            Box(
                modifier = Modifier
                    .border(
                        border = BorderStroke(
                            width = 1.dp,
                            color = borderColor,
                        ),
                        shape = RoundedCornerShape(12.dp),
                    )
                    .padding(
                        horizontal = 12.dp,
                        vertical = 10.dp,
                    )
            ) {
                textField()
            }
        }
    )
}

@Preview
@Composable
fun TextFieldPreview() {
    var text by remember { mutableStateOf("Text value") }

    TextField(
        text = text,
        onTextChanged = {
            text = it
        },
        isError = text.contains(Regex("\\d")),
    )
}
