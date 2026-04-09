package com.sigpro.lider.models

import java.math.BigDecimal

data class PagoRequestDTO(
    val proyectoId: Long,
    val matriculaUsuario: String,
    val monto: BigDecimal,
    val fecha: String? = null
)