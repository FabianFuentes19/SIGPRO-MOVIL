package com.sigpro.lider.api

import com.sigpro.lider.models.LoginRequest
import com.sigpro.lider.models.LoginResponse
import com.sigpro.lider.models.MaterialConAlertaResponseDTO
import com.sigpro.lider.models.MaterialRequestDTO
import com.sigpro.lider.models.MaterialResponseDTO
import com.sigpro.lider.models.PagoConAlertaResponseDTO
import com.sigpro.lider.models.PagoRequestDTO
import com.sigpro.lider.models.PagoResponseDTO
import com.sigpro.lider.models.ProyectoResponseDTO
import com.sigpro.lider.models.UsuarioDTO
import com.sigpro.lider.models.UsuarioRequestDTO
import com.sigpro.lider.models.VoucherDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("proyectos/mi-proyecto/lider")
    suspend fun obtenerProyectoLider(): Response<ProyectoResponseDTO>

    @GET("usuarios/{matricula}")
    suspend fun obtenerPerfil(@Path("matricula") matricula: String): Response<UsuarioDTO>

    @GET("pagos/mis-pagos")
    suspend fun obtenerMisPagos(): Response<List<PagoResponseDTO>>

    @GET("usuarios/lider/{matricula}")
    suspend fun obtenerMiembrosPorMatricula(@Path("matricula") matricula: String): Response<List<UsuarioDTO>>

    @GET("proyectos/mi-equipo")
    suspend fun obtenerEquipoCompleto(): Response<List<UsuarioDTO>>

    @GET("api/materiales/proyecto/{proyectoId}")
    suspend fun obtenerMateriales(@Path("proyectoId") proyectoId: Long): Response<List<MaterialResponseDTO>>

    @POST("api/materiales")
    suspend fun registrarMaterial(@Body material: MaterialRequestDTO): Response<MaterialConAlertaResponseDTO>

    @POST("proyectos/{proyectoId}/miembros")
    suspend fun registrarMiembro(@Path("proyectoId") proyectoId: Long, @Body usuario: UsuarioDTO): Response<Map<String, String>>

    @POST("pagos/registrar")
    suspend fun registrarPago(@Body request: PagoRequestDTO): Response<PagoConAlertaResponseDTO>

    @GET("pagos/proyecto")
    suspend fun consultarPagosProyecto(): Response<List<PagoResponseDTO>>
    @POST("auth/forgot-password")
    suspend fun solicitarRecuperacion(@Body body: Map<String, String>): Response<Map<String, String>>

    @POST("auth/reset-password")
    suspend fun restablecerPassword(@Body body: Map<String, String>): Response<Map<String, String>>

    @POST("auth/verificar-codigo")
    suspend fun verificarCodigo(@Body body: Map<String, String>): Response<Map<String, String>>

    @GET("pagos/miembro/{matricula}")
    suspend fun obtenerPagosPorMatricula(@Path("matricula") matricula: String): Response<List<PagoResponseDTO>>

    @PATCH("usuarios/{matricula}/desactivar")
    suspend fun eliminarMiembro(
        @Path("matricula") matricula: String): Response<Map<String, Any>>
    @PUT("usuarios/{matricula}")
    suspend fun actualizarUsuario(
        @Path("matricula") matricula: String, @Body dto: UsuarioRequestDTO): Response<UsuarioDTO>
    @GET("proyectos/mi-proyecto/miembro")
    suspend fun obtenerProyectoMiembro(): Response<ProyectoResponseDTO>

    @PUT("usuarios/{matricula}/cambiar-contrasena")
    suspend fun cambiarPassword(
        @Path("matricula") matricula: String, @Body body: Map<String, String>): Response<Map<String, String>>

    @GET("pagos/vouchers/{matricula}")
    suspend fun obtenerVouchersMiembro(@Path("matricula") matricula: String): Response<List<VoucherDTO>>
}