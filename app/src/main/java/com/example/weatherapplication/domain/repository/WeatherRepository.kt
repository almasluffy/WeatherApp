package com.example.weatherapplication.domain.repository

import androidx.lifecycle.LiveData
import com.example.weatherapplication.data.api_services.WeatherApi
import com.example.weatherapplication.data.models.Main
import com.example.weatherapplication.data.models.WeatherResponse
import com.example.weatherapplication.data.weatherDatabase.Weather

interface WeatherRepository {

    //Api
    suspend fun getWeatherResponseByCityName(cityName: String) : WeatherResponse?

    //Database

    fun getWeatherList(): LiveData<List<Weather>>

    fun getWeather(id: Int): LiveData<Weather>

    suspend fun insertWeather(weather: Weather)

    fun getMaxWeatherID(): Int

}