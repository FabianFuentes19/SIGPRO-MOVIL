package com.sigpro.lider.models

import com.google.gson.annotations.SerializedName

data class MaterialResponseDTO(
    @SerializedName("id") val id: Long,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("monto") val monto: Double,
    @SerializedName("cantidad") val cantidad: Int,
    @SerializedName("costoTotal") val costoTotal: Double,
    @SerializedName("proyectoId") val proyectoId: Long
)

data class MaterialRequestDTO(
    val nombre: String,
    val monto: Double,
    val cantidad: Int,
    val proyectoId: Long
)