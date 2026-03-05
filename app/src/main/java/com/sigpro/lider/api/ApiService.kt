package com.sigpro.lider.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interfaz Retrofit para los endpoints REST del backend SIGPRO (rol Líder).
 * Base URL: http://localhost:8080 (emulador: http://10.0.2.2:8080)
 */
interface ApiService {

    /**
     * Ejemplo: health o verificación del backend.
     * Ajusta los endpoints según el contrato real de tu API.
     */
    @GET("lider/health")
    suspend fun health(): Response<Unit>

    // Añade aquí los endpoints específicos del rol Líder según el DFR, por ejemplo:
    // @GET("lider/equipos")
    // suspend fun getEquipos(): Response<List<Equipo>>
    //
    // @GET("lider/reportes/{id}")
    // suspend fun getReporte(@Path("id") id: Long): Response<Reporte>
}
