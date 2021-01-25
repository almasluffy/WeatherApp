package com.example.weatherapplication.data.weatherDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "weather_table")
data class Weather (
    @PrimaryKey(autoGenerate = true)
    val id: Int? = 0,
    val city_name: String? = null,
    val temperature: Double? = null,
    val temp_min: Double? = null,
    val temp_max: Double? = null,
    val feels_like: Double? = null,
    val pressure: Int? = null,
    val saved_date: String? = null
)