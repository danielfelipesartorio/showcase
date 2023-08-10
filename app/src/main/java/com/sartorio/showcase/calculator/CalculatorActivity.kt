@file:OptIn(ExperimentalMaterial3Api::class)

package com.sartorio.showcase.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sartorio.showcase.ui.theme.ShowcaseTheme


class CalculatorActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShowcaseTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel = viewModel<CalculatorViewModel>()
                    val state = viewModel.state

                    Column(modifier = Modifier.fillMaxWidth()) {
                        Display(state)
                        Buttons {
                            viewModel.onAction(it)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Display(state: CalculatorState) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Column {
            Text(
                text = state.lastOperation,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.End
            )
            Text(
                text = state.number1 + (state.operation?.symbol ?: "") + state.number2,
                style = MaterialTheme.typography.displayLarge,
                textAlign = TextAlign.End
            )
        }

    }
}

@Composable
fun Buttons(onAction: (CalculatorAction) -> Unit) {
    val buttonSpacing: Dp =8.dp

    Row {
        Column(
            verticalArrangement = Arrangement.spacedBy(buttonSpacing),
            modifier = Modifier.weight(3f)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                CalculatorButton(
                    label = "AC",
                    action = CalculatorAction.Clear,
                    onClick = onAction,
                    modifier = Modifier.weight(2f)
                )
                CalculatorButton(
                    label = "Del",
                    action = CalculatorAction.Delete,
                    onClick = onAction,
                    modifier = Modifier.weight(1f)
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                for (i in 7..9) {
                    CalculatorButton(
                        label = i.toString(),
                        action = CalculatorAction.Number(i),
                        onClick = onAction,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                for (i in 4..6) {
                    CalculatorButton(
                        label = i.toString(),
                        action = CalculatorAction.Number(i),
                        onClick = onAction,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                for (i in 1..3) {
                    CalculatorButton(
                        label = i.toString(),
                        action = CalculatorAction.Number(i),
                        onClick = onAction,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                CalculatorButton(
                    label = "0",
                    action = CalculatorAction.Number(0),
                    onClick = onAction,
                    modifier = Modifier.weight(2f)
                )
                CalculatorButton(
                    label = ".",
                    action = CalculatorAction.Decimal,
                    onClick = onAction,
                    modifier = Modifier.weight(1f)
                )
            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(buttonSpacing),
            modifier = Modifier.weight(1f)
        ) {
            CalculatorButton(
                label = "+",
                action = CalculatorAction.Operation(CalculatorOperation.Add),
                onClick = onAction,
                modifier = Modifier.weight(1f)
            )
            CalculatorButton(
                label = "-",
                action = CalculatorAction.Operation(CalculatorOperation.Subtract),
                onClick = onAction,
                modifier = Modifier.weight(1f)
            )
            CalculatorButton(
                label = "x",
                action = CalculatorAction.Operation(CalculatorOperation.Multiply),
                onClick = onAction,
                modifier = Modifier.weight(1f)
            )
            CalculatorButton(
                label = "/",
                action = CalculatorAction.Operation(CalculatorOperation.Divide),
                onClick = onAction,
                modifier = Modifier.weight(1f)
            )
            CalculatorButton(
                label = "=",
                action = CalculatorAction.Calculate,
                onClick = onAction,
                modifier = Modifier.weight(1f)
            )

        }
    }
}


@Preview
@Composable
fun Preview() {
    Buttons(onAction = {})
}