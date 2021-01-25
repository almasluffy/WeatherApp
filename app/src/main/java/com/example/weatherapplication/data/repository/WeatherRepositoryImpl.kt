package com.example.weatherapplication.data.repository

import androidx.lifecycle.LiveData
import com.example.weatherapplication.data.api_services.WeatherApi
import com.example.weatherapplication.data.models.Main
import com.example.weatherapplication.data.models.WeatherResponse
import com.example.weatherapplication.data.weatherDatabase.Weather
import com.example.weatherapplication.data.weatherDatabase.WeatherDao
import com.example.weatherapplication.domain.repository.WeatherRepository
import com.example.weatherapplication.utils.AppConstants
import retrofit2.await

class WeatherRepositoryImpl(private val weatherApi: WeatherApi, private val weatherDao: WeatherDao): WeatherRepository {


    override suspend fun getWeatherResponseByCityName(cityName: String): WeatherResponse? =
        weatherApi.getWeatherByCityName(cityName).await().body()

    override fun getWeatherList(): LiveData<List<Weather>>  = weatherDao.getWeatherList()

    override fun getWeather(id: Int): LiveData<Weather> = weatherDao.getWeather(id)

    override suspend fun insertWeather(weather: Weather) = weatherDao.insert(weather)

    override fun getMaxWeatherID(): Int = weatherDao.getMaxWeatherID()

}