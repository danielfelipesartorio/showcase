package com.sartorio.showcase

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.sartorio.showcase.calculator.CalculatorActivity
import com.sartorio.showcase.ui.theme.ShowcaseTheme
import com.sartorio.showcase.weather.WeatherActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            ShowcaseTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainMenu()
                }
            }
        }
    }
}

@Composable
fun MainMenu() {

    Column {
        Text(
            text = "Chose you demo!",
        )
        ManuButton(label =  "Weather Forecast", clazz =WeatherActivity::class.java)
        ManuButton(label =  "Calculator", clazz =CalculatorActivity::class.java)
    }
}

@Composable
fun <T : ComponentActivity> ManuButton(label: String, clazz: Class<T>) {
    val context = LocalContext.current

    Button(onClick = {
        context.startActivity(Intent(context,clazz))
    }, modifier = Modifier.fillMaxWidth()) {
        Text(text = label)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ShowcaseTheme {
        MainMenu()
    }
}