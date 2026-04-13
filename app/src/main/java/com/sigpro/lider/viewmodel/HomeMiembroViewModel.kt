package com.sigpro.lider.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sigpro.lider.api.ApiClient
import com.sigpro.lider.models.ProyectoResponseDTO
import com.sigpro.lider.models.UsuarioDTO
import com.sigpro.lider.session.SessionManager
import kotlinx.coroutines.launch

class HomeMiembroViewModel : ViewModel() {
    var proyecto by mutableStateOf<ProyectoResponseDTO?>(null)
    var miembroDetallado by mutableStateOf<UsuarioDTO?>(null)

    var cargando by mutableStateOf(false)
    var errorMensaje by mutableStateOf<String?>(null)

    var usuario by mutableStateOf<UsuarioDTO?>(null)

    var cargandoPassword by mutableStateOf(false)
    var mensajePassword by mutableStateOf<String?>(null)

    fun actualizarContrasena(actual: String, nueva: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            cargandoPassword = true
            mensajePassword = null

            try {
                val matricula = SessionManager.getMatricula() ?: ""
                val body = mapOf(
                    "actual" to actual,
                    "nueva" to nueva
                )

                val response = ApiClient.apiService.cambiarPassword(matricula, body)

                if (response.isSuccessful) {
                    mensajePassword = null
                    onSuccess()
                } else {
                    val errorMsg = response.errorBody()?.string() ?: ""
                    Log.e("API_ERROR_PASS", "Código: ${response.code()}, Body: $errorMsg")

                    mensajePassword = if (errorMsg.contains("actual") || response.code() == 400) {
                        "Contraseña actual incorrecta"
                    } else {
                        "Error al cambiar contraseña"
                    }
                }
            } catch (e: Exception) {
                Log.e("API_ERROR_PASS", "Fallo total: ${e.message}")
                mensajePassword = "Error de conexión"
            } finally {
                cargandoPassword = false
            }
        }
    }

    fun limpiarErrorPassword() {
        mensajePassword = null
    }

    fun cargarPerfil(matricula: String) {
        viewModelScope.launch {
            cargando = true
            errorMensaje = null
            try {
                val response = ApiClient.apiService.obtenerPerfil(matricula)
                if (response.isSuccessful) {
                    usuario = response.body()
                } else {
                    errorMensaje = "No se pudo obtener la información (${response.code()})"
                }
            } catch (e: Exception) {
                errorMensaje = "Error de red"
                Log.e("PERFIL_ERROR", e.message ?: "Error desconocido")
            } finally {
                cargando = false
            }
        }
    }

    fun cargarProyecto() {
        viewModelScope.launch {
            cargando = true
            errorMensaje = null
            try {
                val matricula = SessionManager.getMatricula() ?: ""
                val resProyecto = ApiClient.apiService.obtenerProyectoMiembro()

                if (resProyecto.isSuccessful) {
                    proyecto = resProyecto.body()
                } else {
                    errorMensaje = "No se encontró el proyecto asignado"
                }

                val resPerfil = ApiClient.apiService.obtenerPerfil(matricula)
                if (resPerfil.isSuccessful) {
                    miembroDetallado = resPerfil.body()
                    usuario = resPerfil.body()
                }

            } catch (e: Exception) {
                errorMensaje = "Error de red: verifica tu conexión"
            } finally {
                cargando = false
            }
        }
    }
}