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
import kotlinx.coroutines.launch

class HomeLiderViewModel : ViewModel() {
    var proyecto by mutableStateOf<ProyectoResponseDTO?>(null)
    var listaMiembros = mutableStateListOf<UsuarioDTO>()
    var listaPagos = mutableStateListOf<PagoResponseDTO>()
    var listaMateriales = mutableStateListOf<MaterialResponseDTO>()

    var miembroDetallado by mutableStateOf<UsuarioDTO?>(null)

        private set
    var listaPagosMiembro = mutableStateListOf<PagoResponseDTO>()
        private set

    var cargando by mutableStateOf(false)
    var errorMensaje by mutableStateOf<String?>(null)

    val gastoTotal: Double
        get() = listaMateriales.sumOf { it.costoTotal } + listaPagos.sumOf { it.monto }

    val progresoRestante: Float
        get() {
            val total = proyecto?.presupuesto ?: 0.0
            return if (total > 0) {
                ((total - gastoTotal) / total).toFloat().coerceIn(0f, 1f)
            } else 0f
        }

    val alertaPresupuesto: String?
        get() {
            val total = proyecto?.presupuesto ?: 0.0
            val disponible = total - gastoTotal

            return when {
                disponible <= 0 && total > 0 -> "Presupuesto agotado"
                progresoRestante <= 0.10f -> "¡CRÍTICO! Queda menos del 10% del presupuesto"
                progresoRestante <= 0.20f -> "Atención: Queda el 20% del presupuesto disponible"
                else -> null
            }
        }

    fun cargarDetalleMiembro(matricula: String, onListo: () -> Unit) {
        viewModelScope.launch {
            try {
                val response = ApiClient.apiService.obtenerPerfil(matricula)
                if (response.isSuccessful) {
                    miembroDetallado = response.body()
                    onListo()
                }
            } catch (e: Exception) {
                Log.e("API_ERROR", "No se pudo obtener el detalle: ${e.message}")
            }
        }
    }
    fun cargarProyecto() {
        viewModelScope.launch {
            cargando = true
            try {
                val matricula = com.sigpro.lider.session.SessionManager.getMatricula() ?: ""

                val resProyecto = ApiClient.apiService.obtenerProyectoLider()
                if (resProyecto.isSuccessful) {
                    proyecto = resProyecto.body()

                    proyecto?.id?.let { id ->
                        val resMat = ApiClient.apiService.obtenerMateriales(id)
                        if (resMat.isSuccessful) {
                            listaMateriales.clear()
                            resMat.body()?.let { listaMateriales.addAll(it) }
                        }

                        val resPagos = ApiClient.apiService.consultarPagosProyecto()
                        if (resPagos.isSuccessful) {
                            listaPagos.clear()
                            resPagos.body()?.let { listaPagos.addAll(it) }
                        }
                    }
                }

                val resMiembros = ApiClient.apiService.obtenerMiembrosPorMatricula(matricula)
                if (resMiembros.isSuccessful) {
                    listaMiembros.clear()
                    resMiembros.body()?.let { listaMiembros.addAll(it) }
                }

            } catch (e: Exception) {
                Log.e("API_ERROR", "Error: ${e.message}")
                errorMensaje = "Error al conectar con el servidor"
            } finally {
                cargando = false
            }
        }
    }

    fun cargarPagosMiembro(matricula: String) {
        viewModelScope.launch {
            cargando = true
            try {
                val response = ApiClient.apiService.obtenerPagosPorMatricula(matricula)
                if (response.isSuccessful) {
                    listaPagosMiembro.clear()
                    response.body()?.let { listaPagosMiembro.addAll(it) }
                }
            } catch (e: Exception) {
                Log.e("API_ERROR", "Error historial: ${e.message}")
            } finally {
                cargando = false
            }
        }
    }

    fun agregarMiembro(context: android.content.Context, nuevoMiembro: UsuarioDTO) {
        viewModelScope.launch {
            try {
                val pId = proyecto?.id
                if (pId == null) {
                    android.widget.Toast.makeText(context, "El proyecto no ha cargado aún", android.widget.Toast.LENGTH_SHORT).show()
                    return@launch
                }
                
                val response = ApiClient.apiService.registrarMiembro(pId, nuevoMiembro)
                if (response.isSuccessful) {
                    android.widget.Toast.makeText(context, "¡Miembro registrado correctamente!", android.widget.Toast.LENGTH_SHORT).show()
                    cargarProyecto()
                } else {
                    val codigoError = response.code()
                    val errorJson = response.errorBody()?.string() ?: ""
                    var mensajeReal = "Error desconocido"
                    try {
                        val jsonObj = org.json.JSONObject(errorJson)
                        if (jsonObj.has("error")) {
                            mensajeReal = jsonObj.getString("error")
                        } else if (jsonObj.has("message")) {
                            mensajeReal = jsonObj.getString("message")
                        } else {
                            mensajeReal = errorJson
                        }
                    } catch (e: Exception) {
                        mensajeReal = errorJson.takeIf { it.isNotBlank() } ?: "Error $codigoError sin mensaje."
                    }

                    Log.e("API_DEBUG", "Código: $codigoError | Mensaje real: $mensajeReal")

                    val mensajeAMostrar = when (codigoError) {
                        403 -> "Acceso Denegado (No tienes permisos o la ruta POST no existe/bloqueada)"
                        404 -> "Ruta no encontrada en el servidor (Falta el endpoint en el Controller)"
                        405 -> "Método POST no soportado. Falta @PostMapping en el backend."
                        else -> mensajeReal.ifBlank { "Error del servidor: $codigoError" }
                    }

                    android.widget.Toast.makeText(context, mensajeAMostrar, android.widget.Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Log.e("API_ERROR", "Excepción: ${e.message}")
                android.widget.Toast.makeText(context, "Error de red: ${e.localizedMessage}", android.widget.Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun editarMiembro(context: android.content.Context, matricula: String, usuarioEditado: UsuarioRequestDTO) {
        viewModelScope.launch {
            try {
                val response = ApiClient.apiService.actualizarUsuario(matricula, usuarioEditado)
                if (response.isSuccessful) {
                    val index = listaMiembros.indexOfFirst { it.matricula == matricula }
                    if (index != -1) {
                        val actual = listaMiembros[index]
                        listaMiembros[index] = actual.copy(
                            nombreCompleto = usuarioEditado.nombreCompleto,
                            grupo = usuarioEditado.grupo,
                            carrera = usuarioEditado.carrera,
                            cuatrimestre = usuarioEditado.cuatrimestre
                        )
                    }
                    android.widget.Toast.makeText(context, "Miembro actualizado correctamente", android.widget.Toast.LENGTH_SHORT).show()
                    Log.d("API_SUCCESS", "Usuario actualizado con éxito")
                } else {
                    android.widget.Toast.makeText(context, "No se pudo actualizar correctamente", android.widget.Toast.LENGTH_SHORT).show()
                    errorMensaje = "Error al actualizar: ${response.code()}"
                }
            } catch (e: Exception) {
                android.widget.Toast.makeText(context, "No se pudo actualizar correctamente", android.widget.Toast.LENGTH_SHORT).show()
                Log.e("API_ERROR", e.message ?: "Error desconocido")
            }
        }
    }

    fun eliminarMiembro(context: android.content.Context, matricula: String) {
        viewModelScope.launch {
            try {
                val response = ApiClient.apiService.eliminarMiembro(matricula)

                if (response.isSuccessful) {
                    listaMiembros.removeAll { it.matricula == matricula }
                    android.widget.Toast.makeText(context, "Miembro eliminado correctamente", android.widget.Toast.LENGTH_SHORT).show()
                } else {
                    android.widget.Toast.makeText(context, "Error al eliminar miembro", android.widget.Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                android.widget.Toast.makeText(context, "Error de red", android.widget.Toast.LENGTH_SHORT).show()
            }
        }
    }
}