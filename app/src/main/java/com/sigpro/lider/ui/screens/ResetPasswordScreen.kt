package com.sigpro.lider.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.MaterialTheme
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.tv.material3.OutlinedButtonDefaults

@Composable
fun ResetPasswordScreen(
    onBackToLogin: () -> Unit,
) {
    val context = LocalContext.current

    val utezBlue = Color(0xFF00385F)
    val appBg = Color(0xFFF2F2F2)
    val fieldBg = Color(0xFFF0F0F0)
    val fieldShape = RoundedCornerShape(14.dp)

    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var newPasswordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    var errorMessage by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(appBg)
            .padding(24.dp),
    ) {
        IconButton(
            onClick = onBackToLogin,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 8.dp)
                .background(Color(0xFFE0ECF4), shape = RoundedCornerShape(999.dp))
                .size(40.dp),
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Regresar",
                tint = utezBlue,
            )
        }

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(
                    text = "Restablecer Contraseña",
                    color = utezBlue,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )

                Text(
                    text = "Por favor, ingresa tu nueva contraseña a continuación para asegurar tu cuenta.",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )

                OutlinedTextField(
                    value = newPassword,
                    onValueChange = { v ->
                        newPassword = v
                        if (isError) {
                            isError = false
                            errorMessage = ""
                        }
                    },
                    label = { Text("Nueva Contraseña") },
                    singleLine = true,
                    isError = isError,
                    visualTransformation = if (newPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { newPasswordVisible = !newPasswordVisible }) {
                            Icon(
                                imageVector = if (newPasswordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                                contentDescription = if (newPasswordVisible) "Ocultar" else "Mostrar",
                                tint = utezBlue,
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

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { v ->
                        confirmPassword = v
                        if (isError) {
                            isError = false
                            errorMessage = ""
                        }
                    },
                    label = { Text("Confirmar Nueva Contraseña") },
                    singleLine = true,
                    isError = isError,
                    visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                            Icon(
                                imageVector = if (confirmPasswordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                                contentDescription = if (confirmPasswordVisible) "Ocultar" else "Mostrar",
                                tint = utezBlue,
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
                        fontSize = 13.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                    )
                }

                Button(
                    onClick = {
                        val n = newPassword.trim()
                        val c = confirmPassword.trim()

                        if (n.isEmpty() || c.isEmpty()) {
                            isError = true
                            errorMessage = "Por favor completa ambos campos."
                            return@Button
                        }

                        if (n != c) {
                            isError = true
                            errorMessage = "Las contraseñas no coinciden."
                            Toast.makeText(context, "Las contraseñas no coinciden.", Toast.LENGTH_LONG).show()
                            return@Button
                        }

                        // TODO: integrar endpoint de cambio de contraseña
                        Toast.makeText(context, "Contraseña cambiada con éxito.", Toast.LENGTH_LONG).show()
                        onBackToLogin()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = utezBlue),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text("Cambiar Contraseña", color = Color.White, fontWeight = FontWeight.Medium)
                }

                val utezBlue = Color(0xFF00385F)

                OutlinedButton(
                    onClick = onBackToLogin,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(1.dp, utezBlue), // Agregué el borde para que se vea como la imagen
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = utezBlue
                    )
                ) {
                    Text(
                        text = "Cancelar",
                        color = utezBlue,
                        fontWeight = FontWeight.Medium
                    )
                }

                Surface(
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = Color(0xFFE6F5FF),
                    shape = RoundedCornerShape(12.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Text(
                            text = "Sugerencia: Usa al menos 8 caracteres, incluyendo una mezcla de letras, números y símbolos para mayor seguridad.",
                            color = Color(0xFF00385F),
                            fontSize = 13.sp,
                            textAlign = TextAlign.Start,
                        )
                    }
                }
            }
        }
    }
}


