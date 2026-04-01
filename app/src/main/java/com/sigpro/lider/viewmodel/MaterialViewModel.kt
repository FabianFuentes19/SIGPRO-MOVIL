package com.sigpro.lider.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sigpro.lider.models.MaterialModel
import com.sigpro.lider.repository.MaterialRepository
import kotlinx.coroutines.launch

class MaterialViewModel : ViewModel() {
    private val repository = MaterialRepository()


    var listaMateriales by mutableStateOf<List<MaterialModel>>(emptyList())

    var isLoading by mutableStateOf(false)

    var errorMessage by mutableStateOf<String?>(null)

    fun fetchMateriales(proyectoId: Long) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val response = repository.getMaterialesByProyecto(proyectoId)

                if (response.isSuccessful) {
                    listaMateriales = response.body() ?: emptyList()
                } else {
                    errorMessage = "Error del servidor: ${response.code()}"
                }
            } catch (e: Exception) {
                errorMessage = "Error de conexión: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    fun registrarNuevoMaterial(nuevoMaterial: MaterialModel, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val response = repository.saveMaterial(nuevoMaterial)
                if (response.isSuccessful) {
                    onSuccess()
                    fetchMateriales(nuevoMaterial.proyectoId ?: 1L)
                }
            } catch (e: Exception) {
                errorMessage = "No se pudo guardar el material"
            }
        }
    }
}