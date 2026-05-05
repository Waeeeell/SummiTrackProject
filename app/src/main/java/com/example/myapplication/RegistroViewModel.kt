package com.example.myapplication

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegistroViewModel : ViewModel() {

    private val _errorUsuario = MutableLiveData<String?>()
    val errorUsuario: LiveData<String?> = _errorUsuario

    private val _errorEmail = MutableLiveData<String?>()
    val errorEmail: LiveData<String?> = _errorEmail

    private val _registroExitoso = MutableLiveData<Boolean>()
    val registroExitoso: LiveData<Boolean> = _registroExitoso

    fun validarRegistro(usuario: String, email: String) {
        _errorUsuario.value = if (usuario.isBlank()) "El usuario no puede estar vacío" else null
        
        _errorEmail.value = if (email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            "Formato de email no válido"
        } else null

        _registroExitoso.value = _errorUsuario.value == null && _errorEmail.value == null
    }
}
