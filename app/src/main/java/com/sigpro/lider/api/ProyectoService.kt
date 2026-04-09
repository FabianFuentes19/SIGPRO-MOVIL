package com.sigpro.lider.api

import com.sigpro.lider.models.ProyectoResponseDTO
import retrofit2.http.GET
import retrofit2.http.Header

interface ProyectoService {
    @GET("proyectos/mi-proyecto/lider")
    suspend fun obtenerProyectoLider(
        @Header("Authorization") token: String
    ): ProyectoResponseDTO
}