package com.example.weatherapplication.presentation.weather.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import com.example.weatherapplication.R
import com.example.weatherapplication.base.BaseFragment
import org.koin.android.ext.android.inject


class WeatherDetailFragment : BaseFragment() {

    private val viewModel: WeatherDetailViewModel by inject()

    private lateinit var cityName: TextView
    private lateinit var temperature: TextView
    private lateinit var tempMin: TextView
    private lateinit var tempMax: TextView
    private lateinit var feelsLike: TextView
    private lateinit var pressure: TextView
    private lateinit var savedDate: TextView


    private var weatherID: Int? = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        setData()
    }

    override fun bindViews(view: View) = with(view) {
        cityName = view.findViewById(R.id.city_name)
        temperature= view.findViewById(R.id.temperature)
        tempMin= view.findViewById(R.id.tempMin)
        tempMax= view.findViewById(R.id.tempMax)
        feelsLike = view.findViewById(R.id.feelsLike)
        pressure= view.findViewById(R.id.pressure)
        savedDate= view.findViewById(R.id.savedDate)

    }


    @SuppressLint("SetTextI18n")
    override fun setData() {
        weatherID = arguments?.getInt("id")
        weatherID?.let { id ->
            viewModel.getWeather(id)
        }

        viewModel.liveData.observe(viewLifecycleOwner, Observer {result ->

            cityName.text = result.city_name
            temperature.text = "Temperature: " + result.temperature.toString() + "K"
            tempMin.text = "Min temp: " + result.temp_min.toString() + "K"
            tempMax.text = "Max temp: " +result.temp_max.toString() + "K"
            pressure.text = "Pressure: " + result.pressure.toString() + "Pa"
            savedDate.text= result.saved_date.toString()
            feelsLike.text = "Feels like: " + result.feels_like.toString() + "K"
        })

    }
}