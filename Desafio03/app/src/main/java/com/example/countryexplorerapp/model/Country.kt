package com.example.countryexplorerapp.model

// data class para representar un pais
// usamos @serializedname para mapear el nombre del campo json a nuestra variable
// esto es util si el nombre en el json es diferente o para evitar problemas con ofuscacion de codigo
import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("name")
    val name: Name,

    @SerializedName("capital")
    val capital: List<String>?,

    @SerializedName("region")
    val region: String,

    @SerializedName("subregion")
    val subregion: String?,

    @SerializedName("population")
    val population: Long,

    @SerializedName("flags")
    val flags: Flags
)

data class Name(
    @SerializedName("common")
    val common: String
)

data class Flags(
    @SerializedName("png")
    val png: String
)