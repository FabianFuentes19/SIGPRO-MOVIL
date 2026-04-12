package com.sigpro.lider.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sigpro.lider.api.ApiClient
import com.sigpro.lider.models.MaterialResponseDTO
import com.sigpro.lider.models.PagoResponseDTO
import com.sigpro.lider.models.ProyectoResponseDTO
import com.sigpro.lider.models.UsuarioDTO
import com.sigpro.lider.models.UsuarioRequestDTO
import com.sigpro.lider.session.SessionManager
import kotlinx.coroutines.launch

class HomeMiembroViewModel : ViewModel() {
    var proyecto by mutableStateOf<ProyectoResponseDTO?>(null)
    var miembroDetallado by mutableStateOf<UsuarioDTO?>(null)

    var cargando by mutableStateOf(false)
    var errorMensaje by mutableStateOf<String?>(null)

    var usuario by mutableStateOf<UsuarioDTO?>(null)

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
    fun cargarProyecto() {
        viewModelScope.launch {
            cargando = true
            try {
                val matricula = SessionManager.getMatricula() ?: ""

                val resProyecto = ApiClient.apiService.obtenerProyectoMiembro()

                if (resProyecto.isSuccessful) {
                    proyecto = resProyecto.body()
                    Log.d("DEBUG_RETRIEVAL", "Proyecto cargado: ${proyecto?.nombre}")
                } else {
                    val errorBody = resProyecto.errorBody()?.string()
                    Log.e("DEBUG_RETRIEVAL", "Error API: ${resProyecto.code()} - $errorBody")
                    errorMensaje = "No se encontró el proyecto asignado"
                }

                val resPerfil = ApiClient.apiService.obtenerPerfil(matricula)
                if (resPerfil.isSuccessful) {
                    miembroDetallado = resPerfil.body()
                    usuario = resPerfil.body()
                }

            } catch (e: Exception) {
                Log.e("API_ERROR", "Fallo total: ${e.message}")
                errorMensaje = "Error de red: verifica tu conexión"
            } finally {
                cargando = false
            }
        }
    }
}