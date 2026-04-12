package com.sigpro.lider.models

import com.google.gson.annotations.SerializedName

data class UsuarioDTO(
    @SerializedName("id") val id: Long? = null,
    @SerializedName(value = "nombreCompleto", alternate = ["nombre_completo"]) val nombreCompleto: String = "",
    @SerializedName("contrasena") val contrasena: String? = null,
    @SerializedName("grupo") val grupo: String = "",
    @SerializedName("matricula") val matricula: String = "",
    @SerializedName("carrera") val carrera: String = "",
    @SerializedName("cuatrimestre") val cuatrimestre: Int = 0,
    @SerializedName("puesto") val puesto: String = "",
    @SerializedName(value = "salarioQuincenal", alternate = ["salario_quincenal"]) val salarioQuincenal: Double = 0.0,
    @SerializedName(value = "fechaIngreso", alternate = ["fecha_ingreso", "fechaDeIngreso"]) val fechaIngreso: String? = null,
    @SerializedName("estado") val estado: String? = "ACTIVO",
    @SerializedName(value = "rolId", alternate = ["rol_id"]) val rolId: Long? = null,
    @SerializedName(value = "rolNombre", alternate = ["rol_nombre"]) val rolNombre: String? = null
)
