@file:Suppress("unused")

package com.example.compose_sample.modifier

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import com.example.compose_sample.R

@Composable
fun ContentDescriptionSample() {
    Text(
        text = "3 x 200р",
        modifier = Modifier
            .semantics {
                contentDescription = "3 товара по 200р"
                testTag = "amount_and_price"
            }
    )
}

@Composable
fun IconWithContentDescription() {
    // Некоторые функции уже принимают content description. Это тоже самое,
    // что и Modifier.semantics { contentDescription = "" }.
    Icon(
        painter = painterResource(R.drawable.round_mail_24),
        contentDescription = stringResource(R.string.mail),
    )
}

@Composable
fun RoleSample() {
    Text(
        text = "Buy now",
        modifier = Modifier
            .semantics {
                role = Role.Button
            }
    )
}
