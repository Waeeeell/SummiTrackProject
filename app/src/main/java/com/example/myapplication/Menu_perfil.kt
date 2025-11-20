package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Menu_perfil : AppCompatActivity() {

    private lateinit var Volver: ImageView
    private lateinit var LogOut: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_perfil)

        initComponents()
        initListeners()
    }

    private fun initComponents(){
        Volver = findViewById<ImageView>(R.id.volverPerfil)
        LogOut = findViewById<Button>(R.id.LogOut)
    }
    private  fun initListeners(){

        Volver.setOnClickListener {
            val intent = Intent(this, menu_navegacion::class.java)
            startActivity(intent)
        }
        LogOut.setOnClickListener {
            confirmarLogOut()
        }
    }

    private fun confirmarLogOut(){
        AlertDialog.Builder(this)

            .setTitle("Cerrar Sesión")
            .setMessage("Seguro que quieres cerrar sesión con esta cuenta?")
            .setPositiveButton("Sí") { _, _ ->

                val intent = Intent(this@Menu_perfil, MainActivity::class.java)
                startActivity(intent)
            }
            .setNegativeButton("No", null)
            .show()
    }
}