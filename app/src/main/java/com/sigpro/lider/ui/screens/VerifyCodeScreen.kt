package com.sigpro.lider.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun VerifyCodeScreen(
    onBackToForgot: () -> Unit,
    onVerified: () -> Unit,
) {
    val utezBlue = Color(0xFF00385F)
    val teal = Color(0xFF00A88F)
    val appBg = Color(0xFFF2F2F2)

    var d1 by remember { mutableStateOf("") }
    var d2 by remember { mutableStateOf("") }
    var d3 by remember { mutableStateOf("") }
    var d4 by remember { mutableStateOf("") }
    var d5 by remember { mutableStateOf("") }
    var d6 by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(appBg)
            .padding(24.dp),
    ) {
        IconButton(
            onClick = onBackToForgot,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 8.dp)
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
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(
                    text = "Recuperar Contraseña",
                    color = utezBlue,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )

                Text(
                    text = "Ingresa el código de 6 dígitos enviado a tu correo institucional.",
                    color = Color.DarkGray,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(8.dp))

                OtpRow(
                    values = listOf(d1, d2, d3, d4, d5, d6),
                    onValueChange = { index, value ->
                        val v = value.takeLast(1).filter { it.isDigit() }
                        when (index) {
                            0 -> d1 = v
                            1 -> d2 = v
                            2 -> d3 = v
                            3 -> d4 = v
                            4 -> d5 = v
                            5 -> d6 = v
                        }
                    },
                )

                Button(
                    onClick = {
                        // TODO: verificar código con backend.
                        // Por ahora, si el usuario presiona el botón, asumimos éxito y avanzamos.
                        onVerified()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = utezBlue),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                ) {
                    Text("Verificar Código", color = Color.White)
                }

                Text(
                    text = "¿No recibiste el código? ",
                    color = Color.DarkGray,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                )

                Text(
                    text = "Reenviar",
                    color = teal,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable {
                        // TODO: re-enviar código
                    },
                )
            }
        }
    }
}

@Composable
private fun OtpRow(
    values: List<String>,
    onValueChange: (Int, String) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val fieldBg = Color(0xFFF0F0F0)

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        values.forEachIndexed { index, value ->
            OutlinedTextField(
                value = value,
                onValueChange = { newValue ->
                    val filtered = newValue.takeLast(1).filter { it.isDigit() }
                    onValueChange(index, filtered)
                    if (filtered.isNotEmpty() && index < values.lastIndex) {
                        focusManager.moveFocus(androidx.compose.ui.focus.FocusDirection.Next)
                    }
                },
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .weight(1f)
                    .height(55.dp),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = fieldBg,
                    unfocusedContainerColor = fieldBg,
                    disabledContainerColor = fieldBg,
                ),
            )
        }
    }
}
/*
@Preview(showBackground = true)
@Composable
fun VerifyCodeScreenPreview() {
    VerifyCodeScreen(
        onBackToForgot = {},
        onVerified = {},
    )
}
*/
