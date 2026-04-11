package com.sigpro.lider.ui.components

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

data class Pago(val fecha: String, val monto: String)

@Composable
fun HistorialMiembroDialog(
    nombre: String,
    puesto: String,
    totalAcumulado: String,
    listaPagos: List<Pago>,
    cargando: Boolean,
    onDismiss: () -> Unit
) {
    val azulMarino = Color(0xFF00334E)
    val verdeBoton = Color(0xFF00897B)

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.85f)
        ) {
            Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Historial", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = azulMarino, modifier = Modifier.align(Alignment.Start))

                Spacer(Modifier.height(24.dp))

                // Perfil del miembro
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.size(80.dp).background(verdeBoton, CircleShape), contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Person, null, tint = Color.White, modifier = Modifier.size(50.dp))
                    }
                    Spacer(Modifier.width(16.dp))
                    Column {
                        Text(nombre, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text(puesto, fontSize = 14.sp, color = Color.Gray)
                        Text("Total acumulado", fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(top = 8.dp))
                        Text(totalAcumulado, color = verdeBoton, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                }

                Spacer(Modifier.height(24.dp))
                Divider()
                Spacer(Modifier.height(16.dp))

                Text("Historial de pagos", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = azulMarino, modifier = Modifier.align(Alignment.Start))

                // Área de contenido con Loader
                Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    if (cargando) {
                        CircularProgressIndicator(color = verdeBoton)
                    } else if (listaPagos.isEmpty()) {
                        Text("No hay pagos registrados todavía", color = Color.Gray, textAlign = TextAlign.Center)
                    } else {
                        LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(vertical = 12.dp)) {
                            items(listaPagos) { pago ->
                                Card(elevation = 4.dp, shape = RoundedCornerShape(12.dp), modifier = Modifier.padding(vertical = 6.dp).fillMaxWidth()) {
                                    Row(Modifier.padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Column {
                                            Text("FECHA DE PAGO", fontSize = 10.sp, color = Color.Gray)
                                            Text(pago.fecha, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                        }
                                        Column(horizontalAlignment = Alignment.End) {
                                            Text("MONTO", fontSize = 10.sp, color = Color.Gray)
                                            Text(pago.monto, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = verdeBoton)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))
                OutlinedButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth(0.5f).height(45.dp), border = BorderStroke(1.dp, verdeBoton), shape = RoundedCornerShape(12.dp)) {
                    Text("Cerrar", color = verdeBoton, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}