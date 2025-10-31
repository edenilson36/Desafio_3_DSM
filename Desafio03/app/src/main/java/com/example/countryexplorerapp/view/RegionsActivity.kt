package com.example.countryexplorerapp.view

import android.content.Intent
import android.os.Bundle
import android.util.Log // <-- importacion necesaria para los logs
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.countryexplorerapp.controller.AppController
import com.example.countryexplorerapp.databinding.ActivityRegionsBinding
import kotlinx.coroutines.launch

// la vista que muestra la lista de regiones
class RegionsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegionsBinding
    private lateinit var regionAdapter: RegionAdapter
    private val controller = AppController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // inflamos el layout usando view binding
        binding = ActivityRegionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        fetchRegions()
    }

    // configuracion inicial del recyclerview
    private fun setupRecyclerView() {
        regionAdapter = RegionAdapter(emptyList()) { region ->
            // esto se ejecuta cuando el usuario hace clic en una region
            val intent = Intent(this, CountryListActivity::class.java)
            intent.putExtra("REGION_NAME", region)
            startActivity(intent)
        }
        binding.recyclerViewRegions.adapter = regionAdapter
    }

    // funcion para obtener las regiones usando el controlador
    private fun fetchRegions() {
        // mostramos el progressbar y ocultamos la lista
        binding.progressBar.visibility = View.VISIBLE
        binding.recyclerViewRegions.visibility = View.GONE

        Log.d("RegionsActivity", "fetchregions: iniciando corutina.")

        // usamos una corutina para la llamada de red
        lifecycleScope.launch {
            val regions = controller.getRegions()

            Log.d("RegionsActivity", "fetchregions: corutina terminada. regiones obtenidas: ${regions.size}")

            // actualizamos la ui en el hilo principal
            binding.progressBar.visibility = View.GONE
            binding.recyclerViewRegions.visibility = View.VISIBLE
            regionAdapter.updateRegions(regions)
        }
    }
}