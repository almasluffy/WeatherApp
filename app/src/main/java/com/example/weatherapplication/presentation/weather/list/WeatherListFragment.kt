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
import com.example.weatherapplication.R
import com.example.weatherapplication.base.BaseFragment
import com.example.weatherapplication.data.weatherDatabase.Weather
import kotlinx.android.synthetic.main.fragment_weather_list.*
import org.koin.android.ext.android.inject


class WeatherListFragment : BaseFragment() {

    private val viewModel: WeatherListViewModel by inject()
    private lateinit var navController: NavController
    private lateinit var rvWeather: RecyclerView

    companion object {
        fun newInstance() : WeatherListFragment =
            WeatherListFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weather_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        setAdapter()
        setData()


        saveWeather.setOnClickListener {
            viewModel.setCurrentWeather()
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
        rvWeather = findViewById(R.id.rvWeatherList)
        rvWeather.layoutManager = LinearLayoutManager(context)

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