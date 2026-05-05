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
        viewModel.validarRegistro("", "test@test.com")
        assertEquals("El usuario no puede estar vacío", viewModel.errorUsuario.value)
        assertFalse(viewModel.registroExitoso.value ?: false)
    }

    @Test
    fun `email invalido devuelve error`() {
        viewModel.validarRegistro("Wael", "email_incorrecto")
        assertEquals("Formato de email no válido", viewModel.errorEmail.value)
        assertFalse(viewModel.registroExitoso.value ?: false)
    }

    @Test
    fun `datos validos devuelven exito`() {
        viewModel.validarRegistro("Wael", "wael@gmail.com")
        assertNull(viewModel.errorUsuario.value)
        assertNull(viewModel.errorEmail.value)
        assertTrue(viewModel.registroExitoso.value ?: true)
    }
}
