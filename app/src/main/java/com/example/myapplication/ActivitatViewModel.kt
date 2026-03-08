package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ActivitatViewModel : ViewModel() {

    private val _activitats = MutableLiveData<List<Activitat>>()
    val activitats: LiveData<List<Activitat>> = _activitats

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _operacioExitosa = MutableLiveData<Boolean>()
    val operacioExitosa: LiveData<Boolean> = _operacioExitosa

    // GET - Carregar totes les activitats
    fun carregarActivitats() {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = ApiClient.API().getAllActivitats()
                if (response.isSuccessful) {
                    _activitats.value = response.body() ?: emptyList()
                } else {
                    _error.value = "Error al carregar: ${response.code()} - ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Error de connexió: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    // POST - Crear una nova activitat
    fun crearActivitat(activitat: Activitat) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = ApiClient.API().createActivitat(activitat)
                if (response.isSuccessful) {
                    _operacioExitosa.value = true
                    carregarActivitats()
                } else {
                    _error.value = "Error al crear: ${response.code()} - ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Error de connexió: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    // PUT - Actualitzar una activitat
    fun actualitzarActivitat(id: Long, activitat: Activitat) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = ApiClient.API().updateActivitat(id, activitat)
                if (response.isSuccessful) {
                    _operacioExitosa.value = true
                    carregarActivitats()
                } else {
                    _error.value = "Error al actualitzar: ${response.code()} - ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Error de connexió: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    // DELETE - Eliminar una activitat
    fun eliminarActivitat(id: Long) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = ApiClient.API().deleteActivitat(id)
                if (response.isSuccessful) {
                    _operacioExitosa.value = true
                    carregarActivitats()
                } else {
                    _error.value = "Error al eliminar: ${response.code()} - ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Error de connexió: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    // GET - Filtrar per distància mínima
    fun filtrarPerDistancia(distanciaMin: Int) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = ApiClient.API().filtrarPerDistancia(distanciaMin)
                if (response.isSuccessful) {
                    _activitats.value = response.body() ?: emptyList()
                } else {
                    _error.value = "Error al filtrar: ${response.code()} - ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Error de connexió: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }
}
