package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class menu_registrarse : AppCompatActivity() {

    private lateinit var volver: Button
    private lateinit var registrate: Button

    private lateinit var IntroduceUsuario: EditText
    private  lateinit var IntroduceContraseña: EditText

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
        IntroduceContraseña = findViewById<EditText>(R.id.IntroduceContraseña)
        IntroduceUsuario = findViewById<EditText>(R.id.IntroduceUsuario)
    }
    private fun initListeners(){
        volver.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        registrate.setOnClickListener {

            val nombre = IntroduceUsuario.text.toString().trim()
            val password = IntroduceContraseña.text.toString().trim()

            if (nombre.isEmpty()){
                IntroduceUsuario.error = "Usuario incorrecto"
            } else if (password.isEmpty()){
                IntroduceContraseña.error = "Contraseña incorrecta"
            } else {

                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("nombre", nombre)
                startActivity(intent)
            }
        }
    }
}

