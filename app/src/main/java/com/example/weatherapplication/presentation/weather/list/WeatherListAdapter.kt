package com.example.weatherapplication.presentation.weather.list

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
        val cityName: TextView = itemView.findViewById(R.id.cityName)
        val temp: TextView = itemView.findViewById(R.id.temp)
        val savedDate: TextView = itemView.findViewById(R.id.savedDate)

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
        val current = weatherList[position]
        holder.cityName.text = current.city_name
        holder.temp.text = current.id.toString() + "K"
        holder.savedDate.text = current.saved_date
        holder.setItemClick(current)
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