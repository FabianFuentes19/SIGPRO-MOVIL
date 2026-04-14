package com.sigpro.lider.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MiembroItem(
    nombre: String,
    rol: String,
    onEditar: () -> Unit,
    onBorrar: () -> Unit,
    onDetalles: () -> Unit,
    onHistorial: () -> Unit,
    esActivo: Boolean = true
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = 2.dp,
        backgroundColor = Color.White
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = nombre,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF00334E)
                )
                Text(
                    text = rol,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Box {
                IconButton(onClick = { expanded = true }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Opciones",
                        tint = Color(0xFF003D61)
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    if (esActivo) {
                        DropdownMenuItem(onClick = { expanded = false; onEditar() }) {
                            Text("Editar")
                        }
                    }
                    DropdownMenuItem(onClick = { expanded = false; onDetalles() }) {
                        Text("Ver detalles")
                    }
                    DropdownMenuItem(onClick = { expanded = false; onHistorial() }) {
                        Text("Ver historial")
                    }
                    if (esActivo) {
                        DropdownMenuItem(onClick = { expanded = false; onBorrar() }) {
                            Text("Borrar")
                        }
                    }
                }
            }
        }
    }
}