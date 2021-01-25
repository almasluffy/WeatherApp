package com.example.weatherapplication.data.models

import com.google.gson.annotations.SerializedName

data class Main (
    @SerializedName("temp") val temp: Double? = null,
    @SerializedName("feels_like") val feels_like: Double? = null,
    @SerializedName("temp_min") val temp_min: Double? = null,
    @SerializedName("temp_max") val temp_max: Double? = null,
    @SerializedName("pressure") val pressure: Int? = null,
    @SerializedName("humidity") val humidity: Int? = null
)