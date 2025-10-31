package com.example.countryexplorerapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.countryexplorerapp.R
import com.example.countryexplorerapp.model.Country

// el adaptador para la lista de paises
class CountryAdapter(
    private var countries: List<Country>,
    private val onItemClicked: (Country) -> Unit
) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val flag: ImageView = itemView.findViewById(R.id.imageViewFlag)
        val countryName: TextView = itemView.findViewById(R.id.textViewCountryName)
        val capital: TextView = itemView.findViewById(R.id.textViewCapital)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_country, parent, false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = countries[position]
        holder.countryName.text = country.name.common
        // verificamos si la capital no es nula o vacia antes de mostrarla
        holder.capital.text = country.capital?.firstOrNull() ?: "n/a"

        // usamos glide para cargar la imagen de la bandera desde la url
        Glide.with(holder.itemView.context)
            .load(country.flags.png)
            .into(holder.flag)

        holder.itemView.setOnClickListener {
            onItemClicked(country)
        }
    }

    override fun getItemCount(): Int {
        return countries.size
    }

    fun updateCountries(newCountries: List<Country>) {
        countries = newCountries
        notifyDataSetChanged()
    }
}