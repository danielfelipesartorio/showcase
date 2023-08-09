package com.sartorio.showcase.di

import com.sartorio.showcase.network.ResultCallAdapterFactory
import com.sartorio.showcase.weather.WeatherService
import com.sartorio.showcase.weather.WeatherUseCase
import com.sartorio.showcase.weather.WeatherViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

val appModule = module {
    single { provideForecastApi(retrofit = get()) }
    single { WeatherUseCase(weatherService = get()) }

    viewModel { WeatherViewModel(weatherUseCase = get()) }
}

val networkModule = module {
    factory { provideOkHttpClient() }
    single { provideRetrofit(okHttpClient = get()) }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://api.weatherapi.com/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(ResultCallAdapterFactory())
        .build()
}

class HeaderInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
            .newBuilder()
            .build()
        return chain.proceed(request)
    }
}

fun provideOkHttpClient(): OkHttpClient {
    val logging = HttpLoggingInterceptor()
    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
    return OkHttpClient().newBuilder()
        .addInterceptor(HeaderInterceptor())
        .addInterceptor(logging)
        .build()
}

fun provideForecastApi(retrofit: Retrofit): WeatherService =
    retrofit
        .create(WeatherService::class.java)