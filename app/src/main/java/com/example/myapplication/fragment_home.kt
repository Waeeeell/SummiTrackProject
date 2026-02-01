package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class fragment_home : Fragment() {


    private lateinit var rv: RecyclerView


    private val listaRutas = listOf(
        Activitat("Aneto", "Ruta alta montaña", Distancia = 10, Dias = 0, Horas = 8, Minuts = 12),
        Activitat("Montserrat", "Caminada suau", Distancia = 5, Dias = 0, Horas = 2, Minuts = 30),
        Activitat("Pica d'Estats", "Ruta molt exigent", Distancia = 20, Dias = 1, Horas = 4, Minuts = 0),
        Activitat("Collserola", "Passeig matinal", Distancia = 8, Dias = 0, Horas = 1, Minuts = 45),
        Activitat("Vallibierna", "Alta montaña", Distancia = 12, Dias = 0, Horas = 5, Minuts = 30),
        Activitat("k2", "Expedicio", Distancia = 180, Dias = 52, Horas = 4, Minuts = 0),
        Activitat("Itic", "Passeig matinal", Distancia = 8, Dias = 0, Horas = 1, Minuts = 45)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv = view.findViewById(R.id.recyclerViewActividades)
        val adapter = ActivitatAdapter(listaRutas)

        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(requireContext())

    }
}
