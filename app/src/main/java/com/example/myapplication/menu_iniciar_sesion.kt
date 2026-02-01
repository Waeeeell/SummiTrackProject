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
import kotlin.getValue

class menu_iniciar_sesion : AppCompatActivity() {


    private lateinit var IntroduceUsuario: EditText
    private lateinit var IntroduceContraseña: EditText
    private lateinit var iniciarSesion: Button
    private lateinit var volver: Button


    private val viewModel: IniciarSesionViewModel by viewModels() //importante para el viewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_iniciar_sesion)


        initComponent()
        initListeners()
        setupObservers()
    }

    private fun setupObservers() {

        viewModel.errorUsuario.observe(this) { mensaje ->
            // Si el ViewModel detecta un fallo, lo mostramos en el EditText
            IntroduceUsuario.error = mensaje
        }

        // Lo mismo para la contraseña: reaccionamos al cambio de datos
        viewModel.errorContraseña.observe(this) { mensaje ->
            IntroduceContraseña.error = mensaje
        }

        // Observamos el éxito de la operación (el booleano)
        viewModel.registroExitoso.observe(this) { esValido ->
            if (esValido) {
                // Si la validación es correcta, navegamos a la pantalla principal
                val intent = Intent(this, menu_navegacion::class.java)

                startActivity(intent)
            }
        }
    }

    private fun initComponent(){

        IntroduceUsuario = findViewById<EditText>(R.id.iniciaNombre)
        IntroduceContraseña = findViewById<EditText>(R.id.IniciaContraseña)
        iniciarSesion = findViewById<Button>(R.id.AvanzarAmenu)
        volver = findViewById<Button>(R.id.volverAinicio)
    }

    private fun initListeners(){
        iniciarSesion.setOnClickListener {
            // Recogemos lo que el usuario ha escrito
            val usuario = IntroduceUsuario.text.toString().trim()


            val passwd = IntroduceContraseña.text.toString().trim()


            viewModel.validarInicioSesion(usuario, passwd)
        }

        volver.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}