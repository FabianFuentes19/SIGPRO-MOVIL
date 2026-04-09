package com.sigpro.lider.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
                    val index = _listaNominas.indexOfFirst { it.id == nomina.id }
                    if (index != -1) {
                        _listaNominas[index] = _listaNominas[index].copy(isPagado = true)
                    }
                } else {
                }
            } catch (e: Exception) {
            }
        }
    }
}