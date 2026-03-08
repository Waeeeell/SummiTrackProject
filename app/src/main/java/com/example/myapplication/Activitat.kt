package com.example.myapplication

import com.google.gson.annotations.SerializedName

data class Activitat(
    @SerializedName("id")
    val id: Long? = null,

    @SerializedName("nombreRuta")
    val nombreRuta: String,

    @SerializedName("descripcio")
    val descripcio: String,

    @SerializedName("dias")
    val dias: Int,

    @SerializedName("horas")
    val horas: Int,

    @SerializedName("minuts")
    val minuts: Int,

    @SerializedName("distancia")
    val distancia: Int,

    @SerializedName("dataCreated")
    val dataCreated: String? = null,

    @SerializedName("dateUpdate")
    val dateUpdate: String? = null,

    @SerializedName("imatgeUrl")
    val imatgeUrl: String? = null
)