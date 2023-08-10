package com.sartorio.showcase.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CalculatorButton(
    modifier: Modifier = Modifier,
    label: String,
    action: CalculatorAction,
    onClick: (action:CalculatorAction) -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(100.dp))
            .clickable { onClick(action) }
            .background(
                when (action) {
                    is CalculatorAction.Operation, is CalculatorAction.Calculate -> MaterialTheme.colorScheme.primary
                    is CalculatorAction.Delete, is CalculatorAction.Clear -> MaterialTheme.colorScheme.secondary
                    else -> MaterialTheme.colorScheme.inverseSurface
                }
            )
            .padding(8.dp)
            .then(modifier)
    ) {
        Text(text = label, style = MaterialTheme.typography.displaySmall, color = if (isSystemInDarkTheme()) Color.Black else Color.White )
    }
}