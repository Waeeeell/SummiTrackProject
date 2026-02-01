package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class menu_navegacion : AppCompatActivity() {

    private lateinit var perfil: ImageView
    private lateinit var botttomBar: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_navegacion)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentRecyclerLista, fragment_home()).commit()


        initComponent()
        initListeners()

    }

    private fun initComponent() {
        perfil = findViewById(R.id.fotoPerfil)
        botttomBar = findViewById(R.id.BottomBar)
        botttomBar.selectedItemId = R.id.home
    }

    /*private fun setupRecyclerView() {
        adapter = ActivitatAdapter(listaRutas)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this)
    }*/

    private fun initListeners() {
        perfil.setOnClickListener {
            val intent = Intent(this, Menu_perfil::class.java)
            startActivity(intent)
        }


        botttomBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentRecyclerLista, fragment_home()).commit()
                    true

                }

                R.id.nuevaActividad -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentRecyclerLista, fragment_add_activity()).commit()
                    true
                }

                R.id.ajustes -> {

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentRecyclerLista, fragment_settings()).commit()
                    true
                }


                else -> false
            }

            /*btnFiltrarDistancia.setOnClickListener {

            val filtradas = listaRutas.filter {
                it.descripcion.replace("km", "").toInt() > 10
            }
            adapter.updateList(filtradas)*/
        }
    }
}

