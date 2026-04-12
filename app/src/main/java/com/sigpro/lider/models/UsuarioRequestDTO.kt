package com.sigpro.lider.models // Ajusta al paquete de tu proyecto

import java.math.BigDecimal

data class UsuarioRequestDTO(
    val matricula: String,
    val nombreCompleto: String,
    val contrasena: String,
    val grupo: String,
    val carrera: String,
    val cuatrimestre: Int,
    val puesto: String? = null,
    val salarioQuincenal: BigDecimal? = null,
    val rolId: Long? = 2L // Por defecto 2 para rol "Miembro"
)