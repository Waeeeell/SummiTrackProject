package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class fragment_add_activity : Fragment() {

    private lateinit var nombreActividad: EditText
    private lateinit var descripcionActividad: EditText
    private lateinit var dias: NumberPicker
    private lateinit var horas: NumberPicker
    private lateinit var minutos: NumberPicker
    private lateinit var distancia: NumberPicker
    private lateinit var botonCrear: Button
    private lateinit var botonCancelar: Button
    private lateinit var viewModel: ActivitatViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_add_activity, container, false)

        viewModel = ViewModelProvider(requireActivity())[ActivitatViewModel::class.java]

        initComponent(rootView)
        configuracioPickers()
        initListeners()

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

    private fun configuracioPickers() {
        dias.minValue = 0
        dias.maxValue = 30
        horas.minValue = 0
        horas.maxValue = 23
        minutos.minValue = 0
        minutos.maxValue = 59
        distancia.minValue = 0
        distancia.maxValue = 500
    }

    private fun initListeners() {
        botonCrear.setOnClickListener {
            val nom = nombreActividad.text.toString().trim()
            val desc = descripcionActividad.text.toString().trim()

            if (nom.isEmpty()) {
                Toast.makeText(requireContext(), "El nom no pot estar buit", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (desc.isEmpty()) {
                Toast.makeText(requireContext(), "La descripció no pot estar buida", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 1. Asegúrate de que el objeto novaActivitat use todos los valores de los pickers
            val novaActivitat = Activitat(
                nombreRuta = nom,
                descripcio = desc,
                dias = dias.value,
                horas = horas.value,
                minuts = minutos.value,
                distancia = distancia.value
            )

            // 2. Llama a viewModel.crearActivitat(novaActivitat)
            viewModel.crearActivitat(novaActivitat)

            // 3. Añade un Toast que diga "Enviando actividad a la nube..."
            Toast.makeText(requireContext(), "Enviando actividad a la nube...", Toast.LENGTH_SHORT).show()

            // 5. Asegúrate de que el método limpiarCampos() se llame después de crear la actividad
            limpiarCampos()

            // 4. Navegación para volver automáticamente al fragment_home
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentRecyclerLista, fragment_home())
                .commit()
        }

        botonCancelar.setOnClickListener {
            // Tornar al fragment home
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentRecyclerLista, fragment_home())
                .commit()
        }
    }

    private fun limpiarCampos() {
        nombreActividad.text.clear()
        descripcionActividad.text.clear()
        dias.value = 0
        horas.value = 0
        minutos.value = 0
        distancia.value = 0
    }
}
