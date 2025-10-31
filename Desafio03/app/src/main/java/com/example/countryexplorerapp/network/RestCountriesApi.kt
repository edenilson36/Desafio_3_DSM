package com.example.countryexplorerapp.network

import com.example.countryexplorerapp.model.Country
import retrofit2.http.GET
import retrofit2.http.Path

// interfaz para la api de paises
interface RestCountriesApi {
    // el endpoint ya no necesita la version, porque esta en la baseurl
    @GET("all")
    suspend fun getAllCountries(): List<Country>

    @GET("region/{region}")
    suspend fun getCountriesByRegion(@Path("region") region: String): List<Country>
}