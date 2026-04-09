package com.sigpro.lider.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sigpro.lider.api.ApiClient
import com.sigpro.lider.models.PagoResponseDTO
import com.sigpro.lider.models.UsuarioDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PerfilViewModel : ViewModel() {
    var usuario by mutableStateOf<UsuarioDTO?>(null)
    var cargando by mutableStateOf(false)
    var errorMensaje by mutableStateOf<String?>(null)

    private val _pagos = MutableStateFlow<List<PagoResponseDTO>>(emptyList())
    val pagos = _pagos.asStateFlow()

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
