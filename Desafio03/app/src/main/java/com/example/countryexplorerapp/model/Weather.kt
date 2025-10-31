package com.example.countryexplorerapp.model

import com.google.gson.annotations.SerializedName

// data class para el clima
data class WeatherResponse(
    @SerializedName("current")
    val current: CurrentWeather
)

data class CurrentWeather(
    @SerializedName("temp_c")
    val tempC: Double,

    @SerializedName("condition")
    val condition: WeatherCondition,

    @SerializedName("wind_kph")
    val windKph: Double,

    @SerializedName("humidity")
    val humidity: Int
)

data class WeatherCondition(
    @SerializedName("text")
    val text: String,

    @SerializedName("icon")
    val icon: String
)