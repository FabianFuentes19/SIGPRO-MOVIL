package com.sigpro.lider.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sigpro.lider.api.ApiClient
import com.sigpro.lider.models.PagoResponseDTO
import com.sigpro.lider.models.UsuarioDTO
import com.sigpro.lider.session.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PerfilViewModel : ViewModel() {
    var usuario by mutableStateOf<UsuarioDTO?>(null)
    var cargando by mutableStateOf(false)


    var errorMensaje by mutableStateOf<String?>(null)

    private val _pagos = MutableStateFlow<List<PagoResponseDTO>>(emptyList())
    val pagos = _pagos.asStateFlow()

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
            try {
                val response = ApiClient.apiService.obtenerPerfil(matricula)
                if (response.isSuccessful) {
                    usuario = response.body()
                } else {
                    errorMensaje = "No se pudo obtener la información (${response.code()})"
                }
            } catch (e: Exception) {
                errorMensaje = "No se pudo obtener la información"
                Log.e("PERFIL_ERROR", e.message ?: "Error desconocido")
            } finally {
                cargando = false
            }
        }
    }

    fun cargarHistorialPagos() {
        viewModelScope.launch {
            try {
                val response = ApiClient.apiService.obtenerMisPagos()
                if (response.isSuccessful) {
                    _pagos.value = response.body() ?: emptyList()
                }
            } catch (e: Exception) {
            }
        }
    }
}
