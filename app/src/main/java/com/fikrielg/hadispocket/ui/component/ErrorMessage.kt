package com.fikrielg.hadispocket.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ErrorMessage(
    message: String,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Column() {
            Text(text = message)
            Text(
                text = "retry",
                modifier = Modifier.clickable { onClick() },
                color = Color.Blue
            )
        }
    }
}