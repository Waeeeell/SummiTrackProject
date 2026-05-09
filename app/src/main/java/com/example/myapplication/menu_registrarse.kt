package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class menu_registrarse : AppCompatActivity() {

    private lateinit var volver: Button
    private lateinit var registrate: Button

    private lateinit var IntroduceUsuario: EditText
    private lateinit var IntroduceEmail: EditText
    private lateinit var IntroduceContraseña: EditText

    private val viewModel: RegistroViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_registrarse)

        initComponent()
        initListeners()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.errorUsuario.observe(this) { mensaje ->
            IntroduceUsuario.error = mensaje
        }

        viewModel.errorEmail.observe(this) { mensaje ->
            IntroduceEmail.error = mensaje
        }

        viewModel.errorPassword.observe(this) { mensaje ->
            IntroduceContraseña.error = mensaje
        }

        viewModel.registroExitoso.observe(this) { esValido ->
            if (esValido) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun initComponent(){
        volver = findViewById<Button>(R.id.VolverRg)
        registrate = findViewById<Button>(R.id.Adelante)
        IntroduceUsuario = findViewById<EditText>(R.id.IntroduceUsuario)
        IntroduceEmail = findViewById<EditText>(R.id.IntroduceEmail)
        IntroduceContraseña = findViewById<EditText>(R.id.IntroduceContraseña)
    }

    private fun initListeners(){
        volver.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        registrate.setOnClickListener {
            val nombre = IntroduceUsuario.text.toString().trim()
            val email = IntroduceEmail.text.toString().trim()
            val password = IntroduceContraseña.text.toString().trim()

            viewModel.validarRegistro(nombre, email, password)
        }
    }
}

