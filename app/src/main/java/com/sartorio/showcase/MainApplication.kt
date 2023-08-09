package com.sartorio.showcase

import android.app.Application
import android.util.Log
import com.sartorio.showcase.di.appModule
import com.sartorio.showcase.di.networkModule
import com.sartorio.showcase.network.ResultCallAdapterFactory
import com.sartorio.showcase.weather.WeatherService
import com.sartorio.showcase.weather.WeatherUseCase
import com.sartorio.showcase.weather.WeatherViewModel
import okhttp3.Interceptor
import okhttp3.Interceptor.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(
                networkModule,
                appModule
            )
        }
    }
}

