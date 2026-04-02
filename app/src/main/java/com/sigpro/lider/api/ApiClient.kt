package com.sigpro.lider.api

import com.sigpro.lider.BuildConfig
import com.sigpro.lider.session.SessionManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Cliente para consumir la API REST del backend SIGPRO.
 * Backend: http://localhost:8080 (en emulador usar 10.0.2.2:8080).
 */
object ApiClient {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    /**
     * Interceptor que agrega el header Authorization: Bearer <token>
     * a todas las peticiones, excepto al endpoint de login.
     */
    private val authInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val url = originalRequest.url

        val isLoginRequest = url.encodedPath.endsWith("/auth/login")

        val builder = originalRequest.newBuilder()

        if (!isLoginRequest) {
            val token = SessionManager.getToken()
            if (!token.isNullOrBlank()) {
                builder.addHeader("Authorization", "Bearer $token")
            }
        }

        chain.proceed(builder.build())
    }

    private val corsInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val requestWithOrigin = originalRequest.newBuilder()
            .header("Origin", "http://localhost:8080")
            .build()
        chain.proceed(requestWithOrigin)
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(corsInterceptor)
        .addInterceptor(loggingInterceptor)
        .addInterceptor(authInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080/") // Dirección para que el emulador llegue al localhost
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
    val materialService: MaterialService = retrofit.create(MaterialService::class.java)

}

