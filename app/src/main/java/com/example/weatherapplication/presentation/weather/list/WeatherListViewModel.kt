package com.example.weatherapplication.presentation.weather.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kinopoisk.exceptions.NoConnectionException
import com.example.weatherapplication.base.BaseViewModel
import com.example.weatherapplication.data.models.Main
import com.example.weatherapplication.data.models.WeatherResponse
import com.example.weatherapplication.data.weatherDatabase.Weather
import com.example.weatherapplication.data.weatherDatabase.WeatherDao
import com.example.weatherapplication.domain.repository.WeatherRepository
import com.example.weatherapplication.presentation.weather.di.repositoryModule
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import java.text.SimpleDateFormat
import java.util.*

class WeatherListViewModel(private val weatherRepository: WeatherRepository): BaseViewModel() {

    var liveDataList: LiveData<List<Weather>>

    init {
       liveDataList = weatherRepository.getWeatherList()
    }

    fun setCurrentWeather(){

        uiScope.launch {

            val result1: Int = withContext(IO){
                Log.d("Check", "heyID")
                weatherRepository.getMaxWeatherID()
            }

            val result2: WeatherResponse? = withContext(IO){
                Log.d("Check", "Response")
                weatherRepository.getWeatherResponseByCityName("Almaty")
            }
            var new_id  = result1 + 1
            var main = result2?.main!!

            delay(1L)

            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())

            var weather = Weather(
                new_id,
                "Almaty",
                main.temp,
                main.temp_min,
                main.temp_max,
                main.feels_like,
                main.pressure,
                currentDate
            )

            insertWeather(weather)
        }

    }

    private fun insertWeather(weather: Weather){
        CoroutineScope(
            IO).launch {
            Log.d("Check", "hey")
            weatherRepository.insertWeather(weather)
        }
    }

}