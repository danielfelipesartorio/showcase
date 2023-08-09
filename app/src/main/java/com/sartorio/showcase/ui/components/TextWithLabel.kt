package com.sartorio.showcase.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun TextWithLabel(label: String, value: String) {
    Row {
        Text(text = "$label:", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.size(4.dp))
        Text(text = value)
    }
}