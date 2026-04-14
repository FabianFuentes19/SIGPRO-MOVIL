package com.sigpro.lider.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sigpro.lider.api.ApiClient
import com.sigpro.lider.models.PagoRequestDTO
import com.sigpro.lider.models.VoucherDTO
import com.sigpro.lider.ui.screens.NominaData
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class PagosViewModel : ViewModel() {

    var cargando by mutableStateOf(true)
    private val _listaNominas = mutableStateListOf<NominaData>()
    val listaNominas: List<NominaData> get() = _listaNominas
    private var _proyectoId: Long = 1L
    var mostrarAlerta by mutableStateOf(false)
    var mensajeAlerta by mutableStateOf("")
    var colorAlerta by mutableStateOf(Color(0xFFFFB300))
    val listaVouchers = mutableStateListOf<VoucherDTO>()

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

                val resEquipo = ApiClient.apiService.obtenerEquipoCompleto()
                if (resEquipo.isSuccessful) {
                    val equipo = resEquipo.body() ?: emptyList()
                    val hoy = LocalDate.now()

                    // Consultar vouchers en paralelo para cada integrante del equipo (incluyendo líder)
                    val todasNominas = equipo.map { integrante ->
                        viewModelScope.async {
                            try {
                                val resVouchers = ApiClient.apiService.obtenerVouchersMiembro(integrante.matricula)
                                if (resVouchers.isSuccessful) {
                                    val vouchers = resVouchers.body() ?: emptyList()
                                    // Filtrar vouchers: PAGADOS o PENDIENTES ya vencidos
                                    vouchers.filter { v ->
                                        val cleanerDate = v.fechaFin?.split(" ")?.get(0) ?: ""
                                        val fFin = try { LocalDate.parse(cleanerDate) } catch(e:Exception) { null }
                                        val estaPagado = v.estado.equals("PAGADO", ignoreCase = true)
                                        val yaVencio = fFin != null && (hoy.isAfter(fFin) || hoy.isEqual(fFin))
                                        estaPagado || yaVencio
                                    }.map { v ->
                                        NominaData(
                                            id = integrante.id?.toInt() ?: 0,
                                            nombre = integrante.nombreCompleto,
                                            matricula = integrante.matricula,
                                            puesto = integrante.puesto,
                                            voucher = "V-${v.pagoId ?: v.numeroQuincena}",
                                            monto = (v.montoPagado ?: v.montoEsperado).toBigDecimal(),
                                            fecha = v.fechaFin ?: hoy.toString(),
                                            isPagado = v.estado.equals("PAGADO", ignoreCase = true)
                                        )
                                    }
                                } else emptyList()
                            } catch (e: Exception) {
                                Log.e("PagosViewModel", "Error cargando vouchers de ${integrante.matricula}: ${e.message}")
                                emptyList()
                            }
                        }
                    }.awaitAll().flatten()

                    _listaNominas.addAll(todasNominas)
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
                fechaCorte = nomina.fecha ?: LocalDate.now().toString(),
                fechaPagoReal = LocalDate.now().toString()
            )

            try {
                val response = ApiClient.apiService.registrarPago(request)

                if (response.isSuccessful) {
                    val body = response.body()
                    
                    // Si el back trae alertas, las usamos prioritariamente
                    if (body?.alerta != null) {
                        val al = body.alerta
                        mensajeAlerta = al.mensaje
                        colorAlerta = if (al.tipo == "CRITICAL") Color(0xFFE53935) else Color(0xFFFFB300)
                        mostrarAlerta = true
                    } else {
                        actualizarEstadoYPresupuestoManual()
                    }
                    
                    cargarNominas() // Refrescar lista
                } else {
                    Log.e("API_ERROR", "Error en registro: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("API_FAILURE", "Fallo de red", e)
            }
        }
    }

    fun cargarVouchers(matricula: String) {
        viewModelScope.launch {
            cargando = true
            listaVouchers.clear()
            try {
                // Obtener fecha fin del proyecto
                val resProy = ApiClient.apiService.obtenerProyectoLider()
                val fechaFinProy = resProy.body()?.fechaFin
                
                val response = ApiClient.apiService.obtenerPagosPorMatricula(matricula)
                if (response.isSuccessful) {
                    val pagos = response.body() ?: emptyList()
                    
                    // Mapeo de Pagos Reales a VoucherDTO para la UI del Historial
                    val historial = pagos.mapIndexed { index, p ->
                        VoucherDTO(
                            numeroQuincena = index + 1,
                            fechaInicio = p.fechaCorte,
                            fechaFin = p.fechaCorte,
                            montoEsperado = p.monto,
                            estado = "PAGADO",
                            pagoId = p.id,
                            fechaPagoReal = p.fechaPagoReal,
                            montoPagado = p.monto,
                            puesto = ""
                        )
                    }
                    listaVouchers.addAll(historial)
                }
            } catch (e: Exception) {
                Log.e("VOUCHERS_ERROR", e.message ?: "Error desconocido")
            } finally {
                cargando = false
            }
        }
    }

    private fun actualizarEstadoYPresupuestoManual() {
        viewModelScope.launch {
            val resProyecto = ApiClient.apiService.obtenerProyectoLider()
            if (resProyecto.isSuccessful) {
                val p = resProyecto.body() ?: return@launch

                val presupuestoBase = if ((p.presupuestoAutorizado ?: 0.0) > 0.0)
                    p.presupuestoAutorizado!!
                else
                    (p.presupuestoInicial ?: 1.0)

                val presupuestoActual = p.presupuesto ?: 0.0
                val porcentaje = (presupuestoActual / presupuestoBase) * 100

                if (porcentaje <= 20) {
                    mensajeAlerta = if (porcentaje <= 10) "Menos del 10% de presupuesto" else "Menos del 20% de presupuesto"
                    colorAlerta = if (porcentaje <= 10) Color(0xFFE53935) else Color(0xFFFFB300)
                    mostrarAlerta = true
                }
            }
        }
    }
}