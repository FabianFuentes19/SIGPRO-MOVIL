package com.sigpro.lider.models

data class LoginResponse(
    val token: String?,
    val rol: String?,
    val matricula: String?,
    val error: String? // Por si el login falla y el back manda un error
)