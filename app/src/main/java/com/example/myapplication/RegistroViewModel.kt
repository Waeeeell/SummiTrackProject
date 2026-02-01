package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


import androidx.lifecycle.ViewModel // No olvidar este import

class RegistroViewModel : ViewModel() {

    // 1. La versión "Mutable": Solo el ViewModel puede escribir aquí (es privada)
    private val _errorUsuario = MutableLiveData<String?>()

    // 2. La versión "pública": La Activity la lee, pero no puede cambiarla
    val errorUsuario: LiveData<String?> = _errorUsuario

    private val _errorContraseña = MutableLiveData<String?>()

    val errorContraseña: LiveData<String?> = _errorContraseña

    private val _registroExitoso = MutableLiveData<Boolean>()
    val registroExitoso: LiveData<Boolean> = _registroExitoso




    fun validarRegistro(usuario: String, contraseña: String) {    //funcion para validar el registro y contraseña
        if (usuario.isEmpty()) {
            _errorUsuario.value = "Usuario incorrecto"
        } else {
            _errorUsuario.value = null // Todo bien, limpiamos el error
        }

        if (contraseña.isEmpty()){
            _errorContraseña.value = "Contraseña incorrecta"

        } else if (contraseña.length < 8){
            _errorContraseña.value = "La contraseña debe tener al menos 8 caracteres"

        } else {
            _errorContraseña.value = null

        }
        //Si ambos son null, es que no hay errores
        // Actualiza esta línea al final de tu función validarRegistro
        _registroExitoso.value = _errorUsuario.value == null && _errorContraseña.value == null
    }
}