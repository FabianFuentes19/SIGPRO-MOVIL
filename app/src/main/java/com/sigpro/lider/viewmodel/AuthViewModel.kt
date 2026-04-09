package com.sigpro.lider.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sigpro.lider.api.ApiClient
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    var matriculaActual by mutableStateOf("")
    var cargando by mutableStateOf(false)

    var tokenTemporal by mutableStateOf("")
    var nuevaClaveActual by mutableStateOf("")
    var mensajeError by mutableStateOf<String?>(null)
    var pasoCompletado by mutableStateOf(false)

    fun enviarInstrucciones(matricula: String) {
        viewModelScope.launch {
            cargando = true
            mensajeError = null
            try {
                val response = ApiClient.apiService.solicitarRecuperacion(mapOf("matricula" to matricula))
                if (response.isSuccessful) {
                    matriculaActual = matricula
                    pasoCompletado = true
                } else {
                    mensajeError = "No se pudo enviar el correo. Verifica tu matrícula."
                }
            } catch (e: Exception) {
                mensajeError = "Error de conexión con el servidor."
            } finally {
                cargando = false
            }
        }
    }

    fun cambiarPassword(token: String, nuevaClave: String) {
        viewModelScope.launch {
            cargando = true
            mensajeError = null
            try {
                val body = mapOf(
                    "matricula" to matriculaActual,
                    "token" to token,
                    "nuevaContrasena" to nuevaClave
                )
                val response = ApiClient.apiService.restablecerPassword(body)
                if (response.isSuccessful) {
                    pasoCompletado = true
                } else {
                    mensajeError = "Código incorrecto o contraseña no cumple requisitos."
                }
            } catch (e: Exception) {
                mensajeError = "Error al conectar con el servidor."
            } finally {
                cargando = false
            }
        }
    }

    fun resetPaso() { pasoCompletado = false }
}