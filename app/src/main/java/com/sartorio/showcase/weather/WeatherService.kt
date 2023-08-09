package com.sartorio.showcase.weather

import com.sartorio.showcase.data.WeatherData
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("/v1/current.json")
    suspend fun getWeather(
        @Query("q") location: String = "Paris",
        @Query("key") key: String = "edc424ed46b549e6b87202107230708",
    ) : Result<WeatherData>
}