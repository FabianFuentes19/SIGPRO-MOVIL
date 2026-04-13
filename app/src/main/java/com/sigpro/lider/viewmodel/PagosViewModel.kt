package com.sigpro.lider.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sigpro.lider.api.ApiClient
import com.sigpro.lider.models.PagoRequestDTO
import com.sigpro.lider.ui.screens.NominaData
import kotlinx.coroutines.launch
import java.time.LocalDate

class PagosViewModel : ViewModel() {

    var cargando by mutableStateOf(true)
    private val _listaNominas = mutableStateListOf<NominaData>()
    val listaNominas: List<NominaData> get() = _listaNominas
    private var _proyectoId: Long = 1L
    var mostrarAlerta by mutableStateOf(false)
    var mensajeAlerta by mutableStateOf("")
    var colorAlerta by mutableStateOf(Color(0xFFFFB300))

    fun cargarNominas() {
        viewModelScope.launch {
            _listaNominas.clear()
            cargando = true
            try {
                val matricula = com.sigpro.lider.session.SessionManager.getMatricula() ?: ""
                val resProyecto = ApiClient.apiService.obtenerProyectoLider()
                if (resProyecto.isSuccessful) {
                    _proyectoId = resProyecto.body()?.id ?: 1L
                }

                val resMiembros = ApiClient.apiService.obtenerMiembrosPorMatricula(matricula)
                val resPagos = ApiClient.apiService.consultarPagosProyecto()

                if (resMiembros.isSuccessful) {
                    val miembros = resMiembros.body() ?: emptyList()
                    val pagos = if (resPagos.isSuccessful) resPagos.body() ?: emptyList() else emptyList()

                    val nuevasNominas = miembros.mapIndexed { index, miembro ->
                        val pagado = pagos.any { it.matriculaUsuario == miembro.matricula }
                        NominaData(
                            id = miembro.id?.toInt() ?: index,
                            nombre = miembro.nombreCompleto,
                            matricula = miembro.matricula,
                            puesto = miembro.puesto,
                            voucher = "V-${miembro.id ?: index}",
                            monto = miembro.salarioQuincenal.toBigDecimal(),
                            fecha = LocalDate.now().toString(),
                            isPagado = pagado
                        )
                    }
                    _listaNominas.addAll(nuevasNominas)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                cargando = false
            }
        }
    }

    fun registrarPago(nomina: NominaData) {
        viewModelScope.launch {
            val request = PagoRequestDTO(
                proyectoId = _proyectoId,
                matriculaUsuario = nomina.matricula,
                monto = nomina.monto,
                fecha = LocalDate.now().toString()
            )

            try {
                val response = ApiClient.apiService.registrarPago(request)

                if (response.isSuccessful) {
                    cargarNominas()
                    val resProyecto = ApiClient.apiService.obtenerProyectoLider()
                    if (resProyecto.isSuccessful) {
                        val p = resProyecto.body()
                        if (p != null) {
                            val presupuestoBase = if ((p.presupuestoAutorizado ?: 0.0) > 0.0)
                                p.presupuestoAutorizado!!
                            else
                                (p.presupuestoInicial ?: 1.0)
                            val presupuestoActual = p.presupuesto ?: 0.0
                            val porcentaje = (presupuestoActual / presupuestoBase) * 100

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
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}