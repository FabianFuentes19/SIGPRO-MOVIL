package com.sigpro.lider.models

import com.google.gson.annotations.SerializedName

data class UsuarioDTO(
    @SerializedName("id") val id: Long? = null,
    @SerializedName("nombreCompleto") val nombreCompleto: String = "",
    @SerializedName("contrasena") val contrasena: String? = null,
    @SerializedName("grupo") val grupo: String = "",
    @SerializedName("matricula") val matricula: String = "",
    @SerializedName("carrera") val carrera: String = "",
    @SerializedName("cuatrimestre") val cuatrimestre: Int = 0,
    @SerializedName("puesto") val puesto: String = "",
    @SerializedName("salarioQuincenal") val salarioQuincenal: Double = 0.0,
    @SerializedName("estado") val estado: String? = "ACTIVO",
    @SerializedName("rolId") val rolId: Long? = null,
    @SerializedName("rolNombre") val rolNombre: String? = null
)
