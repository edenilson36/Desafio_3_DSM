package com.example.countryexplorerapp.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.coroutines.launch
import com.example.countryexplorerapp.controller.AppController
import com.example.countryexplorerapp.databinding.ActivityCountryDetailBinding
import com.example.countryexplorerapp.model.Country
import java.text.NumberFormat
import java.util.*

// la vista que muestra los detalles de un pais y su clima
class CountryDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCountryDetailBinding
    private val controller = AppController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // obtenemos el string json del intent
        val countryJson = intent.getStringExtra("COUNTRY_DATA")
        if (countryJson != null) {
            // convertimos el json de vuelta a un objeto country
            val country = Gson().fromJson(countryJson, Country::class.java)
            populateCountryData(country)

            // verificamos si el pais tiene capital para buscar el clima
            val capital = country.capital?.firstOrNull()
            if (capital != null) {
                fetchWeatherData(capital)
            } else {
                // si no hay capital, lo indicamos
                binding.textViewWeatherTitle.text = "Clima no disponible (sin capital)"
            }
        }
    }

    // funcion para llenar la ui con los datos del pais
    private fun populateCountryData(country: Country) {
        title = country.name.common

        binding.textViewCountryNameDetail.text = country.name.common
        binding.textViewRegionDetail.text = "${country.region} (${country.subregion ?: "n/a"})"
        binding.textViewCapitalDetail.text = "Capital: ${country.capital?.firstOrNull() ?: "n/a"}"

        // formateamos el numero de poblacion para que sea mas legible
        val populationFormatted = NumberFormat.getNumberInstance(Locale.US).format(country.population)
        binding.textViewPopulationDetail.text = "Poblacion: $populationFormatted"

        // usamos glide para cargar la imagen de la bandera
        Glide.with(this)
            .load(country.flags.png)
            .into(binding.imageViewFlagDetail)
    }

    // funcion para obtener los datos del clima
    private fun fetchWeatherData(capital: String) {
        // actualizamos el titulo de la seccion del clima
        binding.textViewWeatherTitle.text = "Clima Actual en $capital"
        // mostramos el progressbar y ocultamos los datos
        binding.progressBarWeather.visibility = View.VISIBLE
        binding.weatherDataContainer.visibility = View.GONE

        // es importante no subir esta clave a repositorios publicos
        val apiKey = "ad0fc7eacf4049b6bea205024253010"

        lifecycleScope.launch {
            val weatherResponse = controller.getWeatherForCapital(apiKey, capital)
            binding.progressBarWeather.visibility = View.GONE

            if (weatherResponse != null) {
                // si la respuesta es exitosa, mostramos los datos
                binding.weatherDataContainer.visibility = View.VISIBLE
                val weather = weatherResponse.current
                binding.textViewTemperature.text = "Temperatura: ${weather.tempC}°C"
                binding.textViewCondition.text = "Condicion: ${weather.condition.text}"
                binding.textViewWind.text = "Viento: ${weather.windKph} kph"
                binding.textViewHumidity.text = "Humedad: ${weather.humidity}%"
            } else {
                // si hay un error, lo indicamos en el titulo
                binding.textViewWeatherTitle.text = "No se pudo obtener el clima para $capital"
            }
        }
    }
}