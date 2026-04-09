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

    fun limpiarError() {
        mensajeError = null
    }
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

    fun validarCodigo(token: String) {
        viewModelScope.launch {
            cargando = true
            mensajeError = null
            try {
                val body = mapOf(
                    "matricula" to matriculaActual,
                    "token" to token
                )
                val response = ApiClient.apiService.verificarCodigo(body)

                if (response.isSuccessful) {
                    tokenTemporal = token
                    pasoCompletado = true
                } else {
                    mensajeError = "El código ingresado es incorrecto."
                }
            } catch (e: Exception) {
                mensajeError = "Error de conexión con el servidor."
            } finally {
                cargando = false
            }
        }
    }

    fun reenviarCodigo(matricula: String) {
        viewModelScope.launch {
            cargando = true
            mensajeError = null
            try {
                val response = ApiClient.apiService.solicitarRecuperacion(mapOf("matricula" to matricula))

                if (response.isSuccessful) {
                    mensajeError = "Código reenviado con éxito"
                } else {
                    mensajeError = "No se pudo reenviar el código"
                }
            } catch (e: Exception) {
                mensajeError = "Error de red: revisa tu conexión"
            } finally {
                cargando = false
            }
        }
    }

    fun restablecerPassword(nuevaPassword: String) {
        viewModelScope.launch {
            cargando = true
            mensajeError = null
            try {
                val body = mapOf(
                    "matricula" to matriculaActual,
                    "token" to tokenTemporal,
                    "nuevaContrasena" to nuevaPassword
                )
                val response = ApiClient.apiService.restablecerPassword(body)

                if (response.isSuccessful) {
                    pasoCompletado = true
                } else {
                    mensajeError = "No se pudo actualizar. Revisa que cumpla con los requisitos de seguridad."
                }
            } catch (e: Exception) {
                mensajeError = "Error de conexión con el servidor."
            } finally {
                cargando = false
            }
        }
    }

    fun resetPaso() { pasoCompletado = false }
}