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

        Activitat(1, "Ruta Montserrat", "15km"),
        Activitat(2, "Camí de Ronda", "5km"),
        Activitat(3, "Pirineus Central", "20km"),
        Activitat(4, "Collserola", "8km"),
        Activitat(5, "Ruta Montserrat", "15km"),
        Activitat(6, "Camí de Ronda", "5km"),
        Activitat(7, "Pirineus Central", "20km"),
        Activitat(8, "Collserola", "8km")
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
