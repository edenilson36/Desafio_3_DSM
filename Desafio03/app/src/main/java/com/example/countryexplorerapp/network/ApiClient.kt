package com.example.countryexplorerapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// objeto para centralizar la creacion del cliente retrofit
object ApiClient {
    private const val COUNTRIES_BASE_URL = "https://restcountries.com/v3.1/"
    private const val WEATHER_BASE_URL = "http://api.weatherapi.com/v1/"

    // funcion para crear el servicio de la api de paises
    fun createCountriesApi(): RestCountriesApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(COUNTRIES_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(RestCountriesApi::class.java)
    }

    // funcion para crear el servicio de la api del clima
    fun createWeatherApi(): WeatherApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(WEATHER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(WeatherApi::class.java)
    }
}