package com.example.weatherapplication.data.api_services

import com.example.weatherapplication.data.models.WeatherResponse
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    fun getWeatherByCityName(@Query("q") q: String) : Deferred<Response<WeatherResponse>>

}