package com.sigpro.lider.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun ConsultarMiembroDialog(
    // Parámetros que recibe el modal (los datos que queremos ver)
    nombre: String,
    matricula: String,
    cuatrimestre: String,
    grupo: String,
    carrera: String,
    puesto: String,
    salario: String,
    fechaIngreso: String,
    onDismiss: () -> Unit
) {
    val azulMarino = Color(0xFF00334E)
    val verdeBoton = Color(0xFF00897B)
    val grisPuesto = Color(0xFFE0E0E0) // Gris claro para la etiqueta del puesto

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            elevation = 16.dp // Misma elevación que los otros para consistencia
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 20.dp)
                    .verticalScroll(rememberScrollState()), // Por si acaso en pantallas chicas
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // --- TÍTULO ---
                Text(
                    text = "Consultar miembro",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(32.dp))

                // --- DATOS DEL MIEMBRO ---
                // Para no repetir código, usamos una pequeña función auxiliar
                InfoCampo(label = "NOMBRE", value = nombre, colorValue = azulMarino)
                InfoCampo(label = "MATRICULA", value = matricula)

                // Fila para Cuatri y Grupo
                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.weight(1f)) {
                        InfoCampo(label = "CUATRISMSTRE", value = cuatrimestre)
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        InfoCampo(label = "GRUPO", value = grupo)
                    }
                }

                InfoCampo(label = "CARRERA", value = carrera)

                Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                    Text(text = "PUESTO", fontSize = 14.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .background(color = grisPuesto, shape = RoundedCornerShape(16.dp))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = puesto,
                            fontSize = 13.sp,
                            color = azulMarino
                        )
                    }
                }

                InfoCampo(label = "SALARIO QUINCENAL", value = salario)
                InfoCampo(label = "FECHA DE INGRESO", value = fechaIngreso)

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth(0.6f) // No ocupa todo el ancho, es más compacto
                        .height(48.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = verdeBoton),
                ) {
                    Text(
                        text = "Cerrar",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun InfoCampo(label: String, value: String, colorValue: Color = Color.Black) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Text(text = label, fontSize = 14.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = value, fontSize = 16.sp, color = colorValue)
    }
}