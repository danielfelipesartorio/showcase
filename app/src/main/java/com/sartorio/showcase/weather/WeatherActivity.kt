package com.sartorio.showcase.weather

import android.annotation.SuppressLint
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.sartorio.showcase.data.CurrentWeatherData
import com.sartorio.showcase.data.LocationData
import com.sartorio.showcase.extensions.checkLocationPermissionAndExecute
import com.sartorio.showcase.ui.components.Loading
import com.sartorio.showcase.ui.components.TextWithLabel
import com.sartorio.showcase.ui.theme.ShowcaseTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@OptIn(ExperimentalMaterial3Api::class)
class WeatherActivity : ComponentActivity() {
    private val viewModel: WeatherViewModel by viewModel()
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            super.onLocationResult(p0)
            viewModel.getWeather("${p0.lastLocation?.latitude},${p0.lastLocation?.longitude}")
        }

        override fun onLocationAvailability(p0: LocationAvailability) {
            super.onLocationAvailability(p0)
        }
    }

    override fun onDestroy() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
        super.onDestroy()
    }

    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        this@WeatherActivity.checkLocationPermissionAndExecute(
            successCallback = {
                val locationRequest: LocationRequest =
                    LocationRequest.Builder(0L).setMaxUpdates(1).build()
                fusedLocationClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    Looper.getMainLooper()
                )
            }
        )
        setContent {
            val weatherSuccess by viewModel.weatherSuccess.observeAsState(null)
            val weatherError by viewModel.weatherError.observeAsState(null)
            val loading by viewModel.loading.observeAsState(false)

            ShowcaseTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        var text by rememberSaveable { mutableStateOf("") }

                        text.useDebounce(delayMillis = 1000) {
                            viewModel.getWeather(it)
                        }

                        OutlinedTextField(
                            value = text,
                            onValueChange = {
                                text = it
                            },
                            label = { Text("Enter the desired location") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        if (weatherSuccess != null) {
                            Location(weatherSuccess!!.locationData)
                            Divider()
                            CurrentWeather(weatherSuccess!!.currentWeatherData)

                        }
                        if (weatherError != null) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    text = "Something went wrong. Please try again\n${weatherError?.localizedMessage}",
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                    if (loading) Loading()


                }
            }
        }
    }
}

@Composable
fun <T> T.useDebounce(
    delayMillis: Long = 300L,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    onChange: (T) -> Unit
): T {
    val state by rememberUpdatedState(this)
    DisposableEffect(state) {
        val job = coroutineScope.launch {
            delay(delayMillis)
            onChange(state)
        }
        onDispose { job.cancel() }
    }
    return state
}


@Composable
fun Location(locationData: LocationData) {
    Column {
        Text(text = "${locationData.name} - ${locationData.country}")
        TextWithLabel(label = "Lat", value = locationData.lat.toString())
        TextWithLabel(label = "Long", value = locationData.lon.toString())
    }
}

@Composable
fun CurrentWeather(currentWeatherData: CurrentWeatherData) {
    Column {
        Text(text = "Current Weather", style = MaterialTheme.typography.titleLarge)
        TextWithLabel(label = "Condition", value = currentWeatherData.conditionData.text)
        TextWithLabel(
            label = "Temperature",
            value = "${currentWeatherData.tempC}ºC / ${currentWeatherData.tempF}ºF"
        )
        TextWithLabel(
            label = "Feels like",
            value = "${currentWeatherData.feelslikeC}ºC / ${currentWeatherData.feelslikeF}ºF"
        )
        TextWithLabel(label = "Humidity", value = "${currentWeatherData.humidity}%")
        TextWithLabel(
            label = "Precipitation",
            value = "${currentWeatherData.precipMm}mm / ${currentWeatherData.precipIn}in"
        )
        TextWithLabel(label = "Wind Direction", value = currentWeatherData.windDir)
        TextWithLabel(
            label = "Wind Speed",
            value = "${currentWeatherData.windKph}Kph / ${currentWeatherData.windMph}Mph"
        )
        TextWithLabel(label = "Cloud coverage", value = "${currentWeatherData.cloud}%")
    }
}

