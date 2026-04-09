package com.sigpro.lider.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sigpro.lider.R
import com.sigpro.lider.viewmodel.AuthViewModel

@Composable
fun ForgotPasswordScreen(
    onBackToLogin: () -> Unit,
    onGoToVerify: () -> Unit,
    viewModel: AuthViewModel
) {
    val azulUtez = Color(0xFF00385F)
    val appBg = Color(0xFFF2F2F2)
    val grisCampo = Color(0xFFF7F8F9)
    val cardShape = RoundedCornerShape(16.dp)

    var usuario by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel.pasoCompletado) {
        if (viewModel.pasoCompletado) {
            onGoToVerify()
            viewModel.resetPaso()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(appBg)
            .padding(24.dp),
        contentAlignment = Alignment.Center,
    ) {
        Card(
            shape = cardShape,
            backgroundColor = Color.White,
            elevation = 4.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(vertical = 32.dp, horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "¿Olvidaste tu contraseña?",
                    color = azulUtez,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = "Ingresa tu matricula para recibir las instrucciones de recuperación de contraseña",
                    color = Color.Gray,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Matricula *",
                    color = Color.DarkGray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = usuario,
                    onValueChange = {
                        usuario = it
                        isError = false
                        if (viewModel.mensajeError != null) viewModel.limpiarError()
                    },
                    placeholder = { Text("20243ds047", color = Color.LightGray) },
                    singleLine = true,
                    isError = isError,
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_user),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = if (isError) Color.Red else Color.Gray
                        )
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = grisCampo,
                        unfocusedBorderColor = if (isError) Color.Red else Color(0xFFE0E0E0),
                        focusedBorderColor = azulUtez,
                        cursorColor = azulUtez
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                if (isError) {
                    Text(
                        text = "Favor de ingresar tu matrícula",
                        color = Color.Red,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.fillMaxWidth().padding(start = 4.dp)
                    )
                } else {
                    viewModel.mensajeError?.let {
                        Text(
                            text = it,
                            color = Color.Red,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                if (viewModel.cargando) {
                    CircularProgressIndicator(color = Color(0xFF00385F))
                } else {
                    Button(
                        onClick = {
                            if (usuario.isNotBlank()) {
                                viewModel.enviarInstrucciones(usuario)
                            } else {
                                isError = true
                            }
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = azulUtez),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp)
                    ) {
                        Text(
                            text = "Enviar instrucciones",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                    viewModel.mensajeError?.let {
                        Text(it, color = Color.Red, fontSize = 12.sp, textAlign = TextAlign.Center)
                    }

                    TextButton(
                        onClick = onBackToLogin,
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Text(
                            text = "Regresar al inicio de sesión",
                            color = azulUtez,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}