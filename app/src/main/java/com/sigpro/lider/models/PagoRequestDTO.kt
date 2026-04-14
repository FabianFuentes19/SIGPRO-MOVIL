package com.sigpro.lider.models

import java.math.BigDecimal

data class PagoRequestDTO(
    val proyectoId: Long,
    val matriculaUsuario: String,
    val monto: BigDecimal,
    val fechaCorte: String, // Cambiado para coincidir con Java
    val fechaPagoReal: String // Cambiado para coincidir con Java
)