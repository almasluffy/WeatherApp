package com.example.weatherapplication.presentation.weather.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.weatherapplication.R
import com.example.weatherapplication.base.BaseFragment
import com.example.weatherapplication.data.weatherDatabase.Weather
import kotlinx.android.synthetic.main.fragment_weather_list.*
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.*


class WeatherListFragment : BaseFragment() {

    private val viewModel: WeatherListViewModel by inject()
    private lateinit var navController: NavController
    private lateinit var rvWeather: RecyclerView
//    private lateinit var srlMovies: SwipeRefreshLayout

    companion object {
        fun newInstance() : WeatherListFragment =
            WeatherListFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        setAdapter()
        setData()


        saveWeather.setOnClickListener {

            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())

            viewModel.loadCurrentWeather()

            var new_id = viewModel.max_ID + 1
            var main = viewModel.main

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
            viewModel.insertWeather(weather)

        }
    }

    private val onClickListener = object:
        WeatherListAdapter.ItemClickListener {
        override fun onItemClick(item: Weather) {
            val bundle = Bundle()
            item.id?.let { id ->
                bundle.putInt("id", id)
            }

            navController.navigate(
                R.id.action_weatherListFragment_to_weatherDetailsFragment,
                bundle
            )
        }
    }
    private val weatherListAdapter by lazy {
        WeatherListAdapter (
            context = context,
            itemClickListener = onClickListener
        )
    }

    override fun bindViews(view: View) = with(view) {
        navController = Navigation.findNavController(this)
        rvWeather = findViewById(R.id.recyclerView)
        //srlMovies = findViewById(R.id.srlWeatherList)
        rvWeather.layoutManager = LinearLayoutManager(context)

//        srlMovies.setOnRefreshListener {
//            //weatherListAdapter.clearAll()
//            viewModel.loadWeatherList()
//        }
    }


    override fun setData() {
        viewModel.liveDataList.observe(viewLifecycleOwner, Observer { weatherList ->
            weatherListAdapter.setWeatherList(weatherList)
        })
    }

    private fun setAdapter() {
        rvWeather.adapter = weatherListAdapter
    }

}