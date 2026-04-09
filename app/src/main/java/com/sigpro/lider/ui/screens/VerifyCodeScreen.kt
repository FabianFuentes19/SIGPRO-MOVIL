package com.sigpro.lider.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sigpro.lider.viewmodel.AuthViewModel

@Composable
fun VerifyCodeScreen(
    onBackToForgot: () -> Unit,
    onVerified: () -> Unit,
    viewModel: AuthViewModel
) {
    val focusRequesters = remember { List(6) { FocusRequester() } }

    LaunchedEffect(viewModel.pasoCompletado) {
        if (viewModel.pasoCompletado) {
            onVerified()
            viewModel.resetPaso()
        }
    }

    LaunchedEffect(Unit) {
        focusRequesters[0].requestFocus()
    }

    val azulUtez = Color(0xFF00385F)
    val verdeReenviar = Color(0xFF00A88F)
    val rojoError = Color(0xFFD32F2F)
    val appBg = Color(0xFFF2F2F2)
    val grisCampo = Color(0xFFF7F8F9)

    var d1 by remember { mutableStateOf("") }
    var d2 by remember { mutableStateOf("") }
    var d3 by remember { mutableStateOf("") }
    var d4 by remember { mutableStateOf("") }
    var d5 by remember { mutableStateOf("") }
    var d6 by remember { mutableStateOf("") }

    val isCodeComplete = d1.isNotEmpty() && d2.isNotEmpty() && d3.isNotEmpty() &&
            d4.isNotEmpty() && d5.isNotEmpty() && d6.isNotEmpty()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(appBg)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        IconButton(
            onClick = onBackToForgot,
            modifier = Modifier
                .align(Alignment.TopStart)
                .background(Color.White, shape = RoundedCornerShape(50.dp))
                .size(40.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Regresar",
                tint = azulUtez
            )
        }

        Card(
            shape = RoundedCornerShape(16.dp),
            backgroundColor = Color.White,
            elevation = 4.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(vertical = 32.dp, horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Recuperar Contraseña",
                    color = azulUtez,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Ingresa el código de 6 dígitos enviado a tu correo institucional.",
                    color = Color.Gray,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val inputs = listOf(d1, d2, d3, d4, d5, d6)
                    val setFuns = listOf<(String) -> Unit>(
                        { d1 = it }, { d2 = it }, { d3 = it },
                        { d4 = it }, { d5 = it }, { d6 = it }
                    )

                    inputs.forEachIndexed { index, value ->
                        OutlinedTextField(
                            value = value,
                            onValueChange = { newValue ->
                                if (newValue.length <= 1 && newValue.all { it.isDigit() }) {
                                    setFuns[index](newValue)

                                    if (viewModel.mensajeError != null) {
                                        viewModel.limpiarError()
                                    }

                                    if (newValue.isNotEmpty() && index < 5) {
                                        focusRequesters[index + 1].requestFocus()
                                    }
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(60.dp)
                                .focusRequester(focusRequesters[index]),
                            textStyle = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                color = azulUtez
                            ),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            shape = RoundedCornerShape(10.dp),
                            singleLine = true,
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                backgroundColor = grisCampo,
                                unfocusedBorderColor = if (viewModel.mensajeError != null) rojoError else Color(0xFFE0E0E0),
                                focusedBorderColor = if (viewModel.mensajeError != null) rojoError else azulUtez
                            )
                        )
                    }
                }

                viewModel.mensajeError?.let { error ->
                    Text(
                        text = error,
                        color = if (error.contains("éxito", ignoreCase = true)) verdeReenviar else rojoError,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { if (isCodeComplete) {
                        val codigoCompleto = "$d1$d2$d3$d4$d5$d6"
                        viewModel.validarCodigo(codigoCompleto)
                    } },
                    enabled = isCodeComplete && !viewModel.cargando,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = azulUtez,
                        disabledBackgroundColor = Color.Gray,
                        contentColor = Color.White,
                        disabledContentColor = Color.LightGray
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().height(55.dp)
                ) {
                    if (viewModel.cargando) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            text = "Verificar Código",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "¿No recibiste el código? ", color = Color.Gray, fontSize = 14.sp)

                    if (viewModel.cargando) {
                        Text(text = "Enviando...", color = Color.Gray, fontSize = 14.sp)
                    } else {
                        Text(
                            text = "Reenviar",
                            color = verdeReenviar,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable {
                                viewModel.reenviarCodigo(viewModel.matriculaActual)
                            }
                        )
                    }
                }
            }
        }
    }
}