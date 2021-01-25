package com.example.weatherapplication.presentation.weather.list

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapplication.R
import com.example.weatherapplication.base.BaseViewHolder
import com.example.weatherapplication.data.weatherDatabase.Weather

class WeatherListAdapter(
    private val context: Context?,
    private val itemClickListener: ItemClickListener
): RecyclerView.Adapter<WeatherListAdapter.WeatherViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var weatherList = emptyList<Weather>()


    inner class WeatherViewHolder(itemView: View): BaseViewHolder(itemView) {
        private val cityName: TextView = itemView.findViewById(R.id.cityName)
        private val temp: TextView = itemView.findViewById(R.id.temp)
        private val savedDate: TextView = itemView.findViewById(R.id.savedDate)

        fun bind(weather: Weather){
            cityName.text = weather.city_name
            temp.text = weather.temperature.toString() + "K"
            savedDate.text = weather.saved_date
        }

        fun setItemClick(item: Weather) {
            itemView.setOnClickListener{
                itemClickListener.onItemClick(item)
            }
        }

        override fun clear() { }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val itemView = inflater.inflate(R.layout.for_weather, parent, false)
        return WeatherViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val weather = weatherList[position]
        holder.bind(weather)
        holder.setItemClick(weather)
    }

    internal fun setWeatherList(weatherList: List<Weather>) {
        this.weatherList = weatherList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int =
        weatherList.size

    interface ItemClickListener {
        fun onItemClick(item: Weather)
    }
}