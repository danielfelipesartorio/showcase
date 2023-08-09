package com.sartorio.showcase.weather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sartorio.showcase.data.WeatherData
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val weatherUseCase: WeatherUseCase
) : ViewModel(
) {

    private val _weatherSuccess = MutableLiveData<WeatherData?>()
    val weatherSuccess: MutableLiveData<WeatherData?> = _weatherSuccess
    private val _weatherError = MutableLiveData<Throwable?>()
    val weatherError: MutableLiveData<Throwable?> = _weatherError
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading
    fun getWeather(location: String) {
        if (location.isEmpty()) return
        viewModelScope.launch {
            _weatherSuccess.postValue(null)
            _weatherError.postValue(null)
            _loading.postValue(true)
            weatherUseCase.getWeather(location = location).onSuccess {
                _weatherSuccess.postValue(it)
                Log.i("INFO", "success call weather api")
            }.onFailure {
                _weatherError.postValue(it)
                Log.i("INFO", "error call weather api")
            }.also {
                _loading.postValue(false)
            }
        }
    }
}