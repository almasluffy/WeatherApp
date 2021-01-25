package com.example.weatherapplication.data.weatherDatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather_table")
    fun getWeatherList(): LiveData<List<Weather>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(weather: Weather)

    @Query("DELETE FROM weather_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM weather_table WHERE id=:id")
    fun getWeather(id: Int): LiveData<Weather>

    @Query("SELECT id FROM weather_table ORDER BY id DESC LIMIT 1")
    fun getMaxWeatherID(): Int


}