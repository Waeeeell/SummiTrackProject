package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class menu_navegacion : AppCompatActivity() {

    private lateinit var perfil: ImageView
    private lateinit var adapter: ActivitatAdapter
    private lateinit var rv: RecyclerView
    private lateinit var btnFiltrarDistancia: TextView

    private val listaRutas = listOf(
        Activitat("Ruta Montserrat", "15km"),
        Activitat("Camí de Ronda", "5km"),
        Activitat("Pirineus Central", "20km"),
        Activitat("Collserola", "8km")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_navegacion)

        initComponent()
        initListeners()
        setupRecyclerView()
    }

    private fun initComponent() {
        perfil = findViewById(R.id.fotoPerfil)
        rv = findViewById(R.id.recyclerViewActividades)
        btnFiltrarDistancia = findViewById(R.id.activitats_recents)
    }

    private fun setupRecyclerView() {
        adapter = ActivitatAdapter(listaRutas)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this)
    }

    private fun initListeners() {
        perfil.setOnClickListener {
            val intent = Intent(this, Menu_perfil::class.java)
            startActivity(intent)
        }

        btnFiltrarDistancia.setOnClickListener {
            val filtradas = listaRutas.filter { it.categoria.replace("km", "").toInt() > 10 }
            adapter.updateList(filtradas)
        }
    }
}