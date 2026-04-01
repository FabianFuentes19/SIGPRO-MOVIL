package com.sigpro.lider.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

// Clase de datos para representar cada pago
data class Pago(val fecha: String, val monto: String)

@Composable
fun HistorialMiembroDialog(
    nombre: String,
    puesto: String,
    totalAcumulado: String,
    listaPagos: List<Pago>,
    onDismiss: () -> Unit
) {
    val azulMarino = Color(0xFF00334E)
    val verdeBoton = Color(0xFF00897B)
    val verdeFondoIcono = Color(0xFF00897B)

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // --- CABECERA ---
                Text(
                    text = "Historial",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = azulMarino,
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // --- INFO PERFIL ---
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Icono de Perfil Circular
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .background(verdeFondoIcono, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Person, contentDescription = null, tint = Color.White, modifier = Modifier.size(50.dp))
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(text = nombre, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text(text = puesto, fontSize = 14.sp, color = Color.Gray)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Total acumulado", fontSize = 12.sp, color = Color.Gray)
                        Text(text = totalAcumulado, color = verdeBoton, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                Divider()
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Historial de pagos",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = azulMarino,
                    modifier = Modifier.align(Alignment.Start)
                )

                // --- LISTA DE PAGOS ---
                LazyColumn(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 12.dp)
                ) {
                    items(listaPagos) { pago ->
                        Card(
                            elevation = 4.dp,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.padding(vertical = 6.dp).fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text("FECHA DE PAGO", fontSize = 10.sp, color = Color.Gray)
                                    Text(pago.fecha, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                }
                                Column(horizontalAlignment = Alignment.End) {
                                    Text("MONTO", fontSize = 10.sp, color = Color.Gray)
                                    Text(pago.monto, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // --- BOTÓN CERRAR ---
                OutlinedButton(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(0.5f).height(45.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, verdeBoton),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Cerrar", color = verdeBoton, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}