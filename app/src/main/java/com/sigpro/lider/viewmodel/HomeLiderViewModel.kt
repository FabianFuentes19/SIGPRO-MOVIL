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
import com.sigpro.lider.models.VoucherDTO
import com.sigpro.lider.session.SessionManager
import kotlinx.coroutines.launch

class HomeLiderViewModel : ViewModel() {
    var proyecto by mutableStateOf<ProyectoResponseDTO?>(null)
    var listaMiembros = mutableStateListOf<UsuarioDTO>()
    var listaMiembrosAnteriores = mutableStateListOf<UsuarioDTO>()
    var listaPagos = mutableStateListOf<PagoResponseDTO>()
    var listaMateriales = mutableStateListOf<MaterialResponseDTO>()

    var miembroDetallado by mutableStateOf<UsuarioDTO?>(null)
        private set
    var listaPagosMiembro = mutableStateListOf<VoucherDTO>()
        private set

    var cargando by mutableStateOf(false)
    var errorMensaje by mutableStateOf<String?>(null)

    val gastoTotal: Double
        get() = (listaMateriales.sumOf { it.costoTotal ?: 0.0 }) +
                (listaPagos.sumOf { it.monto ?: 0.0 })

    val progresoRestante: Float
        get() {
            val total = proyecto?.presupuesto ?: 0.0
            if (total <= 0.0) return 0f
            return ((total - gastoTotal) / total).toFloat().coerceIn(0f, 1f)
        }

    val alertaPresupuesto: String?
        get() {
            val total = proyecto?.presupuesto ?: 0.0
            if (total <= 0.0) return null
            val disponible = total - gastoTotal
            return when {
                disponible <= 0 -> "Presupuesto agotado"
                progresoRestante <= 0.10f -> "¡CRÍTICO! Queda menos del 10% del presupuesto"
                progresoRestante <= 0.20f -> "Atención: Queda el 20% del presupuesto disponible"
                else -> null
            }
        }

    fun cargarProyecto() {
        viewModelScope.launch {
            cargando = true
            errorMensaje = null
            try {
                val matricula = SessionManager.getMatricula() ?: ""
                val resProyecto = ApiClient.apiService.obtenerProyectoLider()
                val proy = resProyecto.body()

                if (resProyecto.isSuccessful && proy != null) {
                    proyecto = proy
                    proy.id?.let { id ->
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

                        val resMiembros = ApiClient.apiService.obtenerMiembrosPorMatricula(matricula)
                        if (resMiembros.isSuccessful) {
                            val todos = resMiembros.body() ?: emptyList()
                            listaMiembros.clear()
                            listaMiembrosAnteriores.clear()
                            
                            listaMiembros.addAll(todos.filter { it.estado != "INACTIVO" })
                            listaMiembrosAnteriores.addAll(todos.filter { it.estado == "INACTIVO" })
                        }
                    }
                } else {
                    proyecto = null
                    listaMateriales.clear()
                    listaPagos.clear()
                    listaMiembros.clear()
                    Log.d("APP_DEBUG", "El líder no tiene proyecto asignado")
                }
            } catch (e: Exception) {
                Log.e("API_ERROR", "Error en carga inicial: ${e.message}")
                errorMensaje = "Error al conectar con el servidor"
            } finally {
                cargando = false
            }
        }
    }

    fun cargarDetalleMiembro(matricula: String, onListo: () -> Unit) {
        viewModelScope.launch {
            miembroDetallado = null // Limpiar previo para asegurar recomposición
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

    fun seleccionarMiembro(miembro: UsuarioDTO) {
        miembroDetallado = miembro
    }

    fun cargarPagosMiembro(matricula: String) {
        viewModelScope.launch {
            cargando = true
            try {
                val response = ApiClient.apiService.obtenerVouchersMiembro(matricula)
                if (response.isSuccessful) {
                    listaPagosMiembro.clear()
                    response.body()?.let { listaPagosMiembro.addAll(it) }
                }
            } catch (e: Exception) {
                Log.e("API_ERROR", "Error historial vouchers: ${e.message}")
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
                    android.widget.Toast.makeText(context, "No tienes un proyecto asignado para agregar miembros", android.widget.Toast.LENGTH_SHORT).show()
                    return@launch
                }
                val response = ApiClient.apiService.registrarMiembro(pId, nuevoMiembro)
                if (response.isSuccessful) {
                    android.widget.Toast.makeText(context, "¡Miembro registrado correctamente!", android.widget.Toast.LENGTH_SHORT).show()
                    cargarProyecto()
                } else {
                    val errorJson = response.errorBody()?.string() ?: ""
                    android.widget.Toast.makeText(context, "Error: $errorJson", android.widget.Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
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
                }
            } catch (e: Exception) {
                android.widget.Toast.makeText(context, "Error al actualizar", android.widget.Toast.LENGTH_SHORT).show()
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
                }
            } catch (e: Exception) {
                android.widget.Toast.makeText(context, "Error de red", android.widget.Toast.LENGTH_SHORT).show()
            }
        }
    }
}