@file:OptIn(ExperimentalMaterial3Api::class)

package com.sartorio.showcase.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sartorio.showcase.ui.theme.ShowcaseTheme

class CalculatorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var text by rememberSaveable { mutableStateOf("0") }

            ShowcaseTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Display(text)
                        Buttons(text)
                    }
                }
            }
        }
    }
}

@Composable
fun Display(text: String) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomStart
    ) {
        Text(
            text,
            Modifier.padding(16.dp),
        )
    }
}

@Composable
fun Buttons(text: String) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxWidth(),
        columns = GridCells.Fixed(3)
    ) {
        items(10){
            Card(
                modifier=  Modifier.padding(16.dp),
                onClick = {}
            ){
                Text(
                    it.toString(),
                    Modifier.padding(16.dp),
                )
            }
        }

    }
}