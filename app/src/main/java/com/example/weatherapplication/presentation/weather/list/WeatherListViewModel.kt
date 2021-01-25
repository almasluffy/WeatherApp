package com.example.weatherapplication.presentation.weather.list

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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherListViewModel(private val weatherRepository: WeatherRepository): BaseViewModel() {

    var liveDataList: LiveData<List<Weather>>

    private val _liveData = MutableLiveData<State>()

     var max_ID: Int = 0
     var main: Main = Main()

    val liveData: LiveData<State>
        get() = _liveData


    init {
        liveDataList = weatherRepository.getWeatherList()
    }

    fun loadWeatherList(){

    }

    fun loadCurrentWeather(){

        uiScope.launch {

            val result1: Int = withContext(IO){
                weatherRepository.getMaxWeatherID()
            }

            val result2: WeatherResponse? = withContext(IO){
                weatherRepository.getWeatherResponseByCityName("Almaty")
            }
            max_ID  = result1
            main = result2?.main!!
//            _liveData.postValue(
//                State.Result(maxID+1, weather?.main!!)
//            )
        }

    }

    fun insertWeather(weather: Weather){
        CoroutineScope(IO).launch {
            weatherRepository.insertWeather(weather)
        }
    }



    override fun handleError(e: Throwable) {
        if (e is NoConnectionException) {
            //ToDo
        }
    }

    sealed class State {
        data class Result(val max_ID: Int, val main: Main): State()
        data class Error(val error: String?): State()
    }

}