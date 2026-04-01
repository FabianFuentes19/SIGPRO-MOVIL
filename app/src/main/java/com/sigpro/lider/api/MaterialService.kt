package com.sigpro.lider.api

import com.sigpro.lider.models.MaterialModel
import retrofit2.Response
import retrofit2.http.*

interface MaterialService {

    @POST("api/materiales")
    suspend fun registrarMaterial(
        @Body material: MaterialModel
    ): Response<MaterialModel>

    @GET("api/materiales/proyecto/{proyectoId}")
    suspend fun listarPorProyecto(
        @Path("proyectoId") proyectoId: Long
    ): Response<List<MaterialModel>>
}