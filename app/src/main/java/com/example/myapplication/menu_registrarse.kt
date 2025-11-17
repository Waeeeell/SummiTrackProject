package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class menu_registrarse : AppCompatActivity() {

    private lateinit var volver: Button
    private lateinit var registrate: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_registrarse)

        initComponent()
        initListeners()
    }

    private fun initComponent(){
        volver = findViewById<Button>(R.id.VolverRg)
        registrate = findViewById<Button>(R.id.Adelante)
    }
    private fun initListeners(){
        volver.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        registrate.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}