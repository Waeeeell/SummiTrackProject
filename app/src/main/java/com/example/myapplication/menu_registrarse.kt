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
    private  lateinit var IntroduceContraseña: EditText

    private val viewModel: RegistroViewModel by viewModels() //declaración viewModel importante

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_registrarse)


        initComponent()
        initListeners()
        setupObservers()


    }

    private fun setupObservers() {            //observadores del viewModel
        // Escuchamos el error del usuario
        viewModel.errorUsuario.observe(this) { mensaje ->
            IntroduceUsuario.error = mensaje
        }

        // Escuchamos el error de la contraseña
        viewModel.errorContraseña.observe(this) { mensaje ->
            IntroduceContraseña.error = mensaje
        }

        // Escuchamos si el registro fue exitoso para cambiar de pantalla
        viewModel.registroExitoso.observe(this) { esValido ->
            if (esValido) {
                val intent = Intent(this, MainActivity::class.java)
                // Aquí puedes pasar el nombre si quieres con intent.putExtra
                startActivity(intent)
            }
        }
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

            // Le pasamos los datos al ViewModel para que trabaje
            viewModel.validarRegistro(nombre, password)

        }
    }
}

