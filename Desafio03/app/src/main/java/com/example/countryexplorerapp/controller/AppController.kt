package com.example.countryexplorerapp.controller

import android.util.Log // <-- importacion necesaria para los logs
import com.example.countryexplorerapp.network.ApiClient

// el controlador que maneja la logica de la aplicacion
class AppController {
    private val countriesApi = ApiClient.createCountriesApi()
    private val weatherApi = ApiClient.createWeatherApi()

    // funcion para obtener una lista unica de regiones
    suspend fun getRegions(): List<String> {
        return try {
            Log.d("AppController", "iniciando llamada de red para obtener paises...")
            val countries = countriesApi.getAllCountries()
            Log.d("AppController", "llamada de red exitosa. se obtuvieron ${countries.size} paises.")
            // usamos distinct para obtener regiones unicas y sorted para ordenarlas
            countries.map { it.region }.distinct().sorted()
        } catch (e: Exception) {
            Log.e("AppController", "error critico en la llamada de red: ${e.message}")
            // si hay un error, devolvemos una lista vacia
            emptyList()
        }
    }

    // funcion para obtener paises por region
    suspend fun getCountriesByRegion(region: String) = try {
        countriesApi.getCountriesByRegion(region)
    } catch (e: Exception) {
        emptyList()
    }

    // funcion para obtener el clima de una capital
    suspend fun getWeatherForCapital(apiKey: String, capital: String) = try {
        weatherApi.getCurrentWeather(apiKey, capital)
    } catch (e: Exception) {
        null
    }
}