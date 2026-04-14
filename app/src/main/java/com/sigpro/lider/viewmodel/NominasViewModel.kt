package com.sigpro.lider.viewmodel

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sigpro.lider.api.ApiClient
import com.sigpro.lider.models.VoucherDTO
import kotlinx.coroutines.launch
import java.time.LocalDate

class NominasMiembroViewModel : ViewModel() {
    var listaVouchers = mutableStateListOf<VoucherDTO>()
    var cargando by mutableStateOf(false)
    var errorMensaje by mutableStateOf<String?>(null)

    fun cargarNominas() {
        viewModelScope.launch {
            cargando = true
            errorMensaje = null
            try {
                val response = ApiClient.apiService.obtenerMisPagos()
                if (response.isSuccessful) {
                    val pagos = response.body() ?: emptyList()
                    
                    // Mapeamos los pagos reales directamente (los que aparecen en tus logs)
                    val voucherList = pagos.mapIndexed { index, p ->
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
                    
                    listaVouchers.clear()
                    listaVouchers.addAll(voucherList)
                } else {
                    errorMensaje = "Error al obtener historial: ${response.code()}"
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