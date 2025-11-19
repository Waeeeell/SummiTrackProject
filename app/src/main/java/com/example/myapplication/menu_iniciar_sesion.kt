package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class menu_iniciar_sesion : AppCompatActivity() {

    private lateinit var IntroduceUsuario: EditText
    private lateinit var IntroduceContraseña: EditText
    private lateinit var iniciarSesion: Button
    private lateinit var volver: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_iniciar_sesion)

        initComponent()
        initListeners()
    }

    private fun initComponent(){
        IntroduceUsuario = findViewById<EditText>(R.id.iniciaNombre)
        IntroduceContraseña = findViewById<EditText>(R.id.IniciaContraseña)
        iniciarSesion = findViewById<Button>(R.id.AvanzarAmenu)
        volver = findViewById<Button>(R.id.volverAinicio)

    }
    private fun initListeners(){

        iniciarSesion.setOnClickListener {
            val usuario = IntroduceUsuario.text.toString().trim()
            val passwd = IntroduceContraseña.text.toString().trim()

            if (usuario.isEmpty()){
                IntroduceUsuario.error = "Usuario incorrecto"
            } else if (passwd.isEmpty()){
                IntroduceContraseña.error = "Contraseña incorrecta"
            } else {
                val intent = Intent(this, menu_navegacion::class.java)
                intent.putExtra("nombre", usuario)
                startActivity(intent)
            }
        }

        volver.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}