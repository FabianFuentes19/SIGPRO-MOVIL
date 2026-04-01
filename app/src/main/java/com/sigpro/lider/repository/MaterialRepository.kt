package com.sigpro.lider.repository

import com.sigpro.lider.api.ApiClient
import com.sigpro.lider.models.MaterialModel
import retrofit2.Response

class MaterialRepository {
    private val api = ApiClient.materialService

    suspend fun getMaterialesByProyecto(proyectoId: Long): Response<List<MaterialModel>> {
        return api.listarPorProyecto(proyectoId)
    }
    suspend fun saveMaterial(material: MaterialModel): Response<MaterialModel> {
        return api.registrarMaterial(material)
    }
}