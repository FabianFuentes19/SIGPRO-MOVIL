package com.sigpro.lider.models

import com.google.gson.annotations.SerializedName

data class ProyectoResponseDTO(
    @SerializedName("id") val id: Long,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("descripcion") val descripcion: String,
    @SerializedName("objetivoGeneral") val objetivoGeneral: String,
    @SerializedName("presupuesto") val presupuesto: Double,
    @SerializedName("presupuestoInicial") val presupuestoInicial: Double,
    @SerializedName("presupuestoAutorizado") val presupuestoAutorizado: Double,
    @SerializedName("fechaInicio") val fechaInicio: String,
    @SerializedName("fechaFin") val fechaFin: String,
    @SerializedName("estado") val estado: String?,
    @SerializedName("miembros") val miembros: List<UsuarioDTO>? = emptyList()
)