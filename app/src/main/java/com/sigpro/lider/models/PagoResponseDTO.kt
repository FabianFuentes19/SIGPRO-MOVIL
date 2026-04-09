package com.sigpro.lider.models

data class PagoResponseDTO(
    val id: Long,
    val proyectoId: Long,
    val matriculaUsuario: String,
    val monto: Double,
    val fecha: String
)