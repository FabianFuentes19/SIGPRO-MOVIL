package com.sigpro.lider.models

import com.google.gson.annotations.SerializedName

data class MaterialModel(
    @SerializedName("id")
    val id: Long? = null, // Usamos Long para que coincida con el ID_PK de Java

    @SerializedName("nombre")
    val nombre: String,

    @SerializedName("monto") // ¡CAMBIADO! Antes era precioUnitario
    val monto: Double,

    @SerializedName("cantidad")
    val cantidad: Int,

    @SerializedName("costoTotal")
    val costoTotal: Double? = null,

    @SerializedName("proyectoId")
    val proyectoId: Long? = null
)