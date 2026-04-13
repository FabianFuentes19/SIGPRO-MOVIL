package com.sigpro.lider.viewmodel

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sigpro.lider.api.ApiClient
import com.sigpro.lider.models.PagoResponseDTO
import kotlinx.coroutines.launch

class NominasMiembroViewModel : ViewModel() {
    var listaPagos = mutableStateListOf<PagoResponseDTO>()
    var cargando by mutableStateOf(false)
    var errorMensaje by mutableStateOf<String?>(null)

    fun cargarNominas() {
        viewModelScope.launch {
            cargando = true
            errorMensaje = null
            try {
                val response = ApiClient.apiService.obtenerMisPagos()
                if (response.isSuccessful) {
                    listaPagos.clear()
                    response.body()?.let { listaPagos.addAll(it) }
                } else {
                    errorMensaje = "Error al obtener nóminas: ${response.code()}"
                }
            } catch (e: Exception) {
                Log.e("NOMINAS_ERROR", e.message ?: "Error desconocido")
                errorMensaje = "Sin conexión al servidor"
            } finally {
                cargando = false
            }
        }
    }
}