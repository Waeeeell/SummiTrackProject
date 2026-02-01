package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class IniciarSesionViewModel : ViewModel() {

    private val _errorUsuario = MutableLiveData<String?>()
    val errorUsuario: LiveData<String?> = _errorUsuario
    private val _errorContraseña = MutableLiveData<String?>()

    val errorContraseña: LiveData<String?> = _errorContraseña

    private val _registroExitoso = MutableLiveData<Boolean>()
    val registroExitoso: LiveData<Boolean> = _registroExitoso


    fun validarInicioSesion(usuario: String, contraseña: String) {


        if (usuario.isEmpty()) {
            _errorUsuario.value = "Usuario incorrecto"
        }  else {
             _errorUsuario.value = null
        }
        if (contraseña.isEmpty()){
            _errorContraseña.value = "Introduzca contraseña"

        } else if (contraseña.length < 8) {
            _errorContraseña.value = "La contraseña debe tener al menos 8 caracteres"

        } else {
            _errorContraseña.value = null
        }
        _registroExitoso.value = _errorUsuario.value == null && _errorContraseña.value == null

    }
}