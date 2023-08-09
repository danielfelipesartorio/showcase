package com.sartorio.showcase.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

@Composable
fun Loading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0f, 0f, 0f, 0.5f)),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Loading",
            textAlign = TextAlign.Center
        )
    }
}