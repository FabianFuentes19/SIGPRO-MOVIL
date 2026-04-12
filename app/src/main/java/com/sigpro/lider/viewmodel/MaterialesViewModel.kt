package com.sigpro.lider.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sigpro.lider.api.ApiClient
import com.sigpro.lider.models.MaterialRequestDTO
import com.sigpro.lider.models.MaterialResponseDTO
import kotlinx.coroutines.launch

class MaterialesViewModel : ViewModel() {
    private val _materiales = mutableStateListOf<MaterialResponseDTO>()

    var textoBusqueda by mutableStateOf("")

    val materialesFiltrados: List<MaterialResponseDTO>
        get() = if (textoBusqueda.isEmpty()) {
            _materiales
        } else {
            _materiales.filter { it.nombre.contains(textoBusqueda, ignoreCase = true) }
        }

    val totalInvertido: Double
        get() = _materiales.sumOf { it.costoTotal }

    var cargando by mutableStateOf(false)

    var mostrarAlerta by mutableStateOf(false)
    var mensajeAlerta by mutableStateOf("")
    var colorAlerta by mutableStateOf(Color(0xFFFFB300))

    fun cargarMateriales(proyectoId: Long) {
        viewModelScope.launch {
            cargando = true
            try {
                val response = ApiClient.apiService.obtenerMateriales(proyectoId)
                if (response.isSuccessful) {
                    _materiales.clear()
                    response.body()?.let { _materiales.addAll(it) }
                }
            } catch (e: Exception) {
                Log.e("API_ERROR", e.message ?: "Error")
            } finally {
                cargando = false
            }
        }
    }

    fun registrarNuevoMaterial(context: Context, nombre: String, cantidad: Int, monto: Double, proyectoId: Long) {
        viewModelScope.launch {
            try {
                val nuevoMaterial = MaterialRequestDTO(nombre, monto, cantidad, proyectoId)
                val response = ApiClient.apiService.registrarMaterial(nuevoMaterial)

                if (response.isSuccessful) {
                    // se recarga materiales
                    cargarMateriales(proyectoId)
                    val resProyecto = ApiClient.apiService.obtenerProyectoLider()
                    if (resProyecto.isSuccessful) {
                        val p = resProyecto.body()
                        if (p != null) {
                            val inicial = p.presupuestoAutorizado ?: 100000.0
                            val actual = p.presupuesto
                            val porcentaje = (actual / inicial) * 100

                            if (porcentaje <= 20) {
                                mensajeAlerta = if (porcentaje <= 10) {
                                    "Te queda menos del 10% de presupuesto"
                                } else {
                                    "Te queda menos del 20% de presupuesto"
                                }
                                colorAlerta = if (porcentaje <= 10) Color(0xFFE53935) else Color(0xFFFFB300)
                                mostrarAlerta = true
                            }
                        }
                    }
                    Toast.makeText(context, "¡Material registrado!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("API_ERROR", "Fallo al registrar: ${e.message}")
            }
        }
    }
}