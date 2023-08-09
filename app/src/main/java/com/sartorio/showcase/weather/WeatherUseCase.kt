package com.sartorio.showcase.weather

import com.sartorio.showcase.data.WeatherData

class WeatherUseCase(
    private val weatherService: WeatherService
) {
    suspend fun getWeather(location: String): Result<WeatherData> {
        return weatherService.getWeather(location=location)
    }

}
