package com.sigpro.lider.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sigpro.lider.api.ApiClient
import com.sigpro.lider.models.ProyectoResponseDTO
import com.sigpro.lider.models.UsuarioDTO
import kotlinx.coroutines.launch
class HomeLiderViewModel : ViewModel() {
    var proyecto by mutableStateOf<ProyectoResponseDTO?>(null)
    var listaMiembros = mutableStateListOf<UsuarioDTO>()
        private set

    var cargando by mutableStateOf(false)
    var errorMensaje by mutableStateOf<String?>(null)

    fun cargarProyecto() {
        viewModelScope.launch {
            cargando = true
            try {
                val matricula = com.sigpro.lider.session.SessionManager.getMatricula() ?: ""

                val resProyecto = ApiClient.apiService.obtenerProyectoLider()
                if (resProyecto.isSuccessful) {
                    proyecto = resProyecto.body()
                }

                val resMiembros = ApiClient.apiService.obtenerMiembrosPorMatricula(matricula)
                if (resMiembros.isSuccessful) {
                    listaMiembros.clear()
                    resMiembros.body()?.let {
                        listaMiembros.addAll(it)
                        Log.d("DEBUG_SIGPRO", "Miembros cargados: ${it.size}")
                    }
                }

            } catch (e: Exception) {
                Log.e("API_ERROR", "Error: ${e.message}")
            } finally {
                cargando = false
            }
        }
    }

    fun agregarMiembro(context: android.content.Context, nuevoMiembro: UsuarioDTO) {
        viewModelScope.launch {
            try {
                val response = ApiClient.apiService.registrarMiembro(nuevoMiembro)
                if (response.isSuccessful) {
                    android.widget.Toast.makeText(context, "¡Miembro registrado!", android.widget.Toast.LENGTH_SHORT).show()
                    cargarProyecto()
                } else {
                    android.widget.Toast.makeText(context, "Error al registrar", android.widget.Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                android.widget.Toast.makeText(context, "Error de red", android.widget.Toast.LENGTH_SHORT).show()
            }
        }
    }

}
