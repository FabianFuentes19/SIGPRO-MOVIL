package com.sigpro.lider.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sigpro.lider.R
import com.sigpro.lider.api.ApiClient
import com.sigpro.lider.models.LoginRequest
import com.sigpro.lider.session.SessionManager
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    onForgotPassword: () -> Unit,
    onLoginSucces: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var user by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val azulUtez = Color(0xFF00385F)
    val grisCampo = Color(0xFFF7F8F9)
    val rojoError = Color.Red

    Scaffold(
        backgroundColor = Color.White
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            Image(
                painter = painterResource(id = R.drawable.logo_utez),
                contentDescription = "Logo UTEZ",
                modifier = Modifier.height(110.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Universidad Tecnológica",
                color = azulUtez,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Del Estado De Morelos",
                color = azulUtez,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Territorio de calidad",
                color = Color.Gray,
                fontSize = 11.sp
            )

            Spacer(modifier = Modifier.height(45.dp))

            Text(
                text = "Bienvenido",
                color = azulUtez,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )
            Text(
                text = "Inicia sesión para acceder a tu cuenta.",
                color = Color.Gray,
                fontSize = 15.sp,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Matrícula *",
                modifier = Modifier.align(Alignment.Start).padding(bottom = 8.dp),
                fontWeight = FontWeight.SemiBold,
                color = if (errorMessage.isNotEmpty()) rojoError else Color.DarkGray
            )
            OutlinedTextField(
                value = user,
                onValueChange = { user = it; errorMessage = "" },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Ingresa tu usuario", color = Color.LightGray) },
                leadingIcon = {
                    Icon(painter = painterResource(id = R.drawable.icon_user),
                        contentDescription = null, modifier = Modifier.size(20.dp), tint = if (errorMessage.isNotEmpty()) rojoError else azulUtez,)
                },
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = grisCampo,
                    unfocusedBorderColor = if (errorMessage.isNotEmpty()) rojoError else Color(0xFFE0E0E0),
                    focusedBorderColor = if (errorMessage.isNotEmpty()) rojoError else azulUtez,
                    cursorColor = azulUtez
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Contraseña *",
                modifier = Modifier.align(Alignment.Start).padding(bottom = 8.dp),
                fontWeight = FontWeight.SemiBold,
                color = if (errorMessage.isNotEmpty()) rojoError else Color.DarkGray

            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it; errorMessage = "" },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("••••••••", color = if (errorMessage.isNotEmpty()) rojoError else azulUtez,) },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                leadingIcon = {
                    Icon(painter = painterResource(id = R.drawable.icon_lock),
                        contentDescription = null, modifier = Modifier.size(20.dp), tint = if (errorMessage.isNotEmpty()) rojoError else azulUtez,)
                },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            contentDescription = null,
                            tint = if (errorMessage.isNotEmpty()) rojoError else azulUtez,
                        )
                    }
                },
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = grisCampo,
                    unfocusedBorderColor = if (errorMessage.isNotEmpty()) rojoError else Color(0xFFE0E0E0),
                    focusedBorderColor = if (errorMessage.isNotEmpty()) rojoError else azulUtez,
                    cursorColor = azulUtez
                )
            )

            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 8.dp).align(Alignment.Start)
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = {
                    if (user.isBlank() || password.isBlank()) {
                        errorMessage = "Favor de completar todos los campos"
                        return@Button
                    }

                    isLoading = true
                    scope.launch {
                        try {
                            val response = ApiClient.apiService.login(LoginRequest(user, password))
                            if (response.isSuccessful) {
                                val token = response.body()?.token
                                if (!token.isNullOrBlank()) {
                                    SessionManager.saveSession(token, response.body()?.rol ?: "")
                                    Toast.makeText(context, "¡Bienvenido!", Toast.LENGTH_SHORT).show()

                                    onLoginSucces()
                                }
                            } else {
                                errorMessage = if (response.code() == 401) "Credenciales incorrectas" else "Error del servidor"
                            }
                        } catch (e: Exception) {
                            android.util.Log.e("LOGIN_ERROR", "Error de red o parseo", e)
                            errorMessage = "Error: ${e.message}"
                        } finally {
                            isLoading = false
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(55.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = azulUtez),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text("Iniciar Sesión", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }

            TextButton(
                onClick =  onForgotPassword,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("¿Olvidaste tu contraseña?", color = azulUtez, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}