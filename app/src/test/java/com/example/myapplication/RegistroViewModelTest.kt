package com.example.myapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class RegistroViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: RegistroViewModel

    @Before
    fun setup() {
        viewModel = RegistroViewModel()
    }

    @Test
    fun `usuario vacio devuelve error`() {
        viewModel.validarRegistro("", "test@test.com", "12345678")
        assertEquals("El usuario no puede estar vacío", viewModel.errorUsuario.value)
        assertFalse(viewModel.registroExitoso.value ?: false)
    }

    @Test
    fun `email invalido devuelve error`() {
        viewModel.validarRegistro("Wael", "email_incorrecto", "12345678")
        assertEquals("Formato de email no válido", viewModel.errorEmail.value)
        assertFalse(viewModel.registroExitoso.value ?: false)
    }

    @Test
    fun `contrasena vacia devuelve error`() {
        viewModel.validarRegistro("Wael", "test@test.com", "")
        assertEquals("La contraseña no puede estar vacía", viewModel.errorPassword.value)
        assertFalse(viewModel.registroExitoso.value ?: false)
    }

    @Test
    fun `contrasena corta devuelve error`() {
        viewModel.validarRegistro("Wael", "test@test.com", "1234")
        assertEquals("La contraseña es demasiado corta", viewModel.errorPassword.value)
        assertFalse(viewModel.registroExitoso.value ?: false)
    }

    @Test
    fun `datos validos devuelven exito`() {
        viewModel.validarRegistro("Wael", "wael@gmail.com", "12345678")
        assertNull(viewModel.errorUsuario.value)
        assertNull(viewModel.errorEmail.value)
        assertNull(viewModel.errorPassword.value)
        assertTrue(viewModel.registroExitoso.value ?: false)
    }
}
