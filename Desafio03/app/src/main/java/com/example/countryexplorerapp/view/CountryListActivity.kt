package com.example.countryexplorerapp.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import kotlinx.coroutines.launch
import com.example.countryexplorerapp.controller.AppController
import com.example.countryexplorerapp.databinding.ActivityCountryListBinding
import com.example.countryexplorerapp.model.Country

// la vista que muestra la lista de paises de una region
class CountryListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCountryListBinding
    private lateinit var countryAdapter: CountryAdapter
    private val controller = AppController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountryListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val regionName = intent.getStringExtra("REGION_NAME")
        if (regionName != null) {
            // establecemos el titulo de la actividad
            title = "Paises de $regionName"
            setupRecyclerView()
            fetchCountries(regionName)
        }
    }

    private fun setupRecyclerView() {
        countryAdapter = CountryAdapter(emptyList()) { country ->
            // cuando se hace clic en un pais, vamos a la pantalla de detalles
            // pasamos el objeto country completo como un string json
            val intent = Intent(this, CountryDetailActivity::class.java)
            intent.putExtra("COUNTRY_DATA", Gson().toJson(country))
            startActivity(intent)
        }
        binding.recyclerViewCountries.adapter = countryAdapter
    }

    // funcion para obtener los paises de la region especificada
    private fun fetchCountries(region: String) {
        binding.progressBar.visibility = View.VISIBLE
        binding.recyclerViewCountries.visibility = View.GONE

        lifecycleScope.launch {
            val countries = controller.getCountriesByRegion(region)
            binding.progressBar.visibility = View.GONE
            binding.recyclerViewCountries.visibility = View.VISIBLE
            countryAdapter.updateCountries(countries)
        }
    }
}