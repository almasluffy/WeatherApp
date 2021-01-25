package com.example.weatherapplication.data.models

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("visibility") val visibility: Int?,
    @SerializedName("timezone") val timezone: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("main") val main: Main?
)