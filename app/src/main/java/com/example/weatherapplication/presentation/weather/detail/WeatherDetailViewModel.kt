package com.example.weatherapplication.presentation.weather.detail


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weatherapplication.base.BaseViewModel
import com.example.weatherapplication.data.weatherDatabase.Weather
import com.example.weatherapplication.domain.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherDetailViewModel(private val weatherRepository: WeatherRepository): BaseViewModel(){


    lateinit var liveData: LiveData<Weather>


    fun getWeather(id: Int){
        viewModelScope.launch {
            val weather = weatherRepository.getWeather(id)
            weather.let {
                liveData = weather
            }
        }
    }
    override fun handleError(e: Throwable) {

    }
}