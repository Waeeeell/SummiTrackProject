package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class menu_navegacion : AppCompatActivity() {

    private lateinit var perfil: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_navegacion)

        initComponent()
        initLiteners()
    }
    private fun initComponent(){
        perfil = findViewById<ImageView>(R.id.fotoPerfil)
    }

    private fun initLiteners(){
        perfil.setOnClickListener {
            val intent = Intent(this, Menu_perfil::class.java)
            startActivity(intent)
        }
    }
}