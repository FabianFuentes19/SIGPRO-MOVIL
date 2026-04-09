package com.sigpro.lider.models

data class AuthResponseDTO(
    val token: String,
    val rol: String,
    val matricula: String,
    val nombreCompleto: String
)