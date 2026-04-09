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
                val nuevoMaterial = MaterialRequestDTO(
                    nombre = nombre,
                    cantidad = cantidad,
                    monto = monto,
                    proyectoId = proyectoId
                )
                val response = ApiClient.apiService.registrarMaterial(nuevoMaterial)

                if (response.isSuccessful) {
                    Toast.makeText(context, "¡Material registrado con éxito!", Toast.LENGTH_SHORT).show()
                    cargarMateriales(proyectoId)
                } else {
                    Toast.makeText(context, "Error: Presupuesto insuficiente", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Error de conexión con el servidor", Toast.LENGTH_SHORT).show()
            }
        }
    }
}