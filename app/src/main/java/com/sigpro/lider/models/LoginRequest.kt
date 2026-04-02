package com.sigpro.lider.models

import com.google.gson.annotations.SerializedName

data class LoginRequest(

    @SerializedName("matricula") 
    val matricula: String,
    
    @SerializedName("contrasena")
    val contrasena: String
)