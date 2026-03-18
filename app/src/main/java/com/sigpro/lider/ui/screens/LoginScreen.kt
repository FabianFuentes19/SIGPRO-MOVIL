package com.sigpro.lider.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import com.sigpro.lider.R
import com.sigpro.lider.api.ApiClient
import com.sigpro.lider.models.LoginRequest
import com.sigpro.lider.session.SessionManager
import kotlinx.coroutines.launch
import org.json.JSONObject

private enum class AuthScreen {
    LOGIN,
    FORGOT_PASSWORD,
}

@Composable
fun LoginScreen() {
    var currentScreen by remember { mutableStateOf(AuthScreen.LOGIN) }

    when (currentScreen) {
        AuthScreen.LOGIN -> LoginContent(
            onForgotPassword = { currentScreen = AuthScreen.FORGOT_PASSWORD },
        )

        AuthScreen.FORGOT_PASSWORD -> ForgotPasswordScreen(
            onBackToLogin = { currentScreen = AuthScreen.LOGIN },
        )
    }
}

@Composable
private fun LoginContent(
    onForgotPassword: () -> Unit,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val utezBlue = Color(0xFF00385F)
    val fieldBg = Color(0xFFF0F0F0)
    val fieldShape: Shape = RoundedCornerShape(14.dp)

    var user by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    fun setError(message: String) {
        isError = true
        errorMessage = message
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_utez),
            contentDescription = "Logo UTEZ",
            modifier = Modifier
                .padding(top = 24.dp)
                .height(120.dp),
        )

        // Textos (universidad)
        Text(
            text = "Universidad Tecnológica",
            color = utezBlue,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
        )
        Text(
            text = "Emiliano Zapata",
            color = utezBlue,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Textos de bienvenida (nuevos)
        Text(
            text = "Bienvenido",
            color = utezBlue,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
        )
        Text(
            text = "Inicia sesión para acceder a tu cuenta.",
            color = Color.DarkGray,
            fontSize = 16.sp,
            modifier = Modifier.fillMaxWidth(),
        )

        OutlinedTextField(
            value = user,
            onValueChange = {
                user = it
                if (isError) isError = false
            },
            label = { Text("Matrícula") },
            singleLine = true,
            isError = isError,
            enabled = !isLoading,
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.icon_user),
                    contentDescription = "Icono usuario",
                    modifier = Modifier.size(24.dp),
                )
            },
            shape = fieldShape,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = fieldBg,
                unfocusedContainerColor = fieldBg,
                disabledContainerColor = fieldBg,
                focusedBorderColor = if (isError) MaterialTheme.colorScheme.error else utezBlue,
                unfocusedBorderColor = if (isError) MaterialTheme.colorScheme.error else Color.Transparent,
                errorBorderColor = MaterialTheme.colorScheme.error,
            ),
            modifier = Modifier.fillMaxWidth(),
        )

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                if (isError) isError = false
            },
            label = { Text("Contraseña") },
            singleLine = true,
            isError = isError,
            enabled = !isLoading,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.icon_lock),
                    contentDescription = "Icono candado",
                    modifier = Modifier.size(24.dp),
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = { passwordVisible = !passwordVisible },
                    enabled = !isLoading,
                ) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                        contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña",
                    )
                }
            },
            shape = fieldShape,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = fieldBg,
                unfocusedContainerColor = fieldBg,
                disabledContainerColor = fieldBg,
                focusedBorderColor = if (isError) MaterialTheme.colorScheme.error else utezBlue,
                unfocusedBorderColor = if (isError) MaterialTheme.colorScheme.error else Color.Transparent,
                errorBorderColor = MaterialTheme.colorScheme.error,
            ),
            modifier = Modifier.fillMaxWidth(),
        )

        if (isError) {
            Text(
                text = errorMessage,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
            )
        }

        Button(
            onClick = {
                val u = user.trim()
                val p = password.trim()

                // Validación 1
                if (u.isEmpty() || p.isEmpty()) {
                    setError("Favor de completar todos los campos de texto")
                    return@Button
                }

                isLoading = true
                isError = false
                errorMessage = ""

                scope.launch {
                    try {
                        val response = ApiClient.apiService.login(
                            LoginRequest(matricula = u, contrasena = p),
                        )

                        if (response.isSuccessful) {
                            val body = response.body()
                            val token = body?.token
                            if (!token.isNullOrBlank()) {
                                SessionManager.saveSession(token, body.rol)
                                Toast.makeText(context, "¡Bienvenido a SIGPRO!", Toast.LENGTH_SHORT).show()
                            } else {
                                setError("Respuesta inválida del servidor.")
                            }
                        } else {
                            // Validación 2 (credenciales incorrectas)
                            if (response.code() == 401) {
                                setError("Contraseña o usuario incorrectos")
                            } else {
                                val serverMsg = try {
                                    val raw = response.errorBody()?.string().orEmpty()
                                    JSONObject(raw).optString("error")
                                } catch (_: Exception) {
                                    ""
                                }
                                setError(if (serverMsg.isNotBlank()) serverMsg else "Error al iniciar sesión.")
                            }
                        }
                    } catch (e: Exception) {
                        setError("Error de conexión: ${e.message ?: "desconocido"}")
                    } finally {
                        isLoading = false
                    }
                }
            },
            enabled = !isLoading,
            colors = ButtonDefaults.buttonColors(containerColor = utezBlue),
            modifier = Modifier.fillMaxWidth(),
        ) {
            if (isLoading) {
                CircularProgressIndicator(strokeWidth = 2.dp, modifier = Modifier.height(18.dp))
            } else {
                Text("Iniciar Sesión", color = Color.White)
            }
        }

        TextButton(
            onClick = onForgotPassword,
            enabled = !isLoading,
        ) {
            Text("¿Olvidaste tu contraseña?", color = utezBlue)
        }
    }
}

