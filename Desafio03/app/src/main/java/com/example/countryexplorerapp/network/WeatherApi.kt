package com.example.countryexplorerapp.network

import retrofit2.http.GET
import retrofit2.http.Query
import com.example.countryexplorerapp.model.WeatherResponse

// interfaz para la api del clima
interface WeatherApi {
    // endpoint para el clima actual
    // pasamos la api key y la ciudad como parametros de consulta (query)
    @GET("current.json")
    suspend fun getCurrentWeather(
        @Query("key") apiKey: String,
        @Query("q") capital: String
    ): WeatherResponse
}