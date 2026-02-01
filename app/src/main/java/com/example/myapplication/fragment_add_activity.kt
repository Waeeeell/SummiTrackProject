package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.TimePicker

class fragment_add_activity : Fragment() {


    private lateinit var nombreActividad: EditText
    private lateinit var descripcionActividad: EditText
    private lateinit var dias: NumberPicker
    private lateinit var horas: NumberPicker
    private lateinit var minutos: NumberPicker
    private lateinit var distancia: NumberPicker

    private lateinit var botonCrear: Button

    private lateinit var botonCancelar: Button




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_add_activity, container, false)

        initComponent(rootView)
        configuracioPickers()

        return rootView


    }

    private fun initComponent(view: View) {
        nombreActividad = view.findViewById(R.id.nombreActividad)
        descripcionActividad = view.findViewById(R.id.descricpcionActividad)
        dias = view.findViewById(R.id.pickerDias)
        horas = view.findViewById(R.id.pickerHoras)
        minutos = view.findViewById(R.id.pickerMinutos)
        distancia = view.findViewById(R.id.pickerDistancia)
        botonCrear = view.findViewById(R.id.aceptarCrear)
        botonCancelar = view.findViewById(R.id.cancelarCrear)
    }

    private fun configuracioPickers(){
        dias.minValue = 0
        dias.maxValue = 30
        horas.minValue = 0
        horas.maxValue = 23
        minutos.minValue = 0
        minutos.maxValue = 59
        distancia.minValue = 0
        distancia.maxValue = 100

    }
}