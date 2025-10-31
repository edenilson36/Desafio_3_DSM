package com.example.countryexplorerapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.countryexplorerapp.R

// el adaptador para la lista de regiones
class RegionAdapter(
    private var regions: List<String>,
    private val onItemClicked: (String) -> Unit
) : RecyclerView.Adapter<RegionAdapter.RegionViewHolder>() {

    // el viewholder contiene las vistas para cada item
    class RegionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val regionName: TextView = itemView.findViewById(R.id.textViewRegionName)
    }

    // este metodo crea un nuevo viewholder inflando el layout del item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_region, parent, false)
        return RegionViewHolder(view)
    }

    // este metodo asocia los datos con el viewholder en una posicion especifica
    override fun onBindViewHolder(holder: RegionViewHolder, position: Int) {
        val region = regions[position]
        holder.regionName.text = region
        // configuramos el click listener para el item
        holder.itemView.setOnClickListener {
            onItemClicked(region)
        }
    }

    // este metodo devuelve el numero total de items en la lista
    override fun getItemCount(): Int {
        return regions.size
    }

    // funcion para actualizar la lista de regiones en el adaptador
    fun updateRegions(newRegions: List<String>) {
        regions = newRegions
        notifyDataSetChanged()
    }
}