package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {


    private lateinit var iniciarSesion: Button
    private lateinit var registrarse: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        initComponents()
        initListeners()

        }

    private fun initComponents(){
        iniciarSesion = findViewById<Button>(R.id.IniciarSesion)
        registrarse = findViewById<Button>(R.id.Registrate)
    }
    private fun initListeners(){
            iniciarSesion.setOnClickListener {
                val intent = Intent(this, menu_navegacion::class.java)
                startActivity(intent)
            }

            registrarse.setOnClickListener {
                val intent = Intent(this, menu_iniciar_sesion::class.java)
                startActivity(intent)
            }
        }
    }
