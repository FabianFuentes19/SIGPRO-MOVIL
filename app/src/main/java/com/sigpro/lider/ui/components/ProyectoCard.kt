package com.sigpro.lider.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.* import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterialApi::class) // 1. Necesario para usar onClick en Surface de Material 2
@Composable
fun ProyectoCard(
    nombre: String,
    objetivo: String,
    fechaInicio: String,
    fechaFin: String,
    presupuesto: String,
    progreso: Float,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        elevation = 4.dp
    ) {
        Column {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(Color(0xFF00897B))
            )

            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = nombre,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF00334E)
                )
                Text(
                    text = objetivo,
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF1F4F9), shape = RoundedCornerShape(8.dp))
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "📅", modifier = Modifier.padding(end = 8.dp))
                    Column {
                        Text("FECHA", fontSize = 10.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                        Text("$fechaInicio — $fechaFin", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("Presupuesto Total", fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                Text(
                    text = "$$presupuesto",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF003D61)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Progreso Del Proyecto", fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                    Text("${(progreso * 100).toInt()}%", fontSize = 12.sp, color = Color(0xFF00897B), fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(4.dp))
                LinearProgressIndicator(
                    progress = progreso,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp),
                    color = Color(0xFF00897B),
                    backgroundColor = Color(0xFFE0E0E0)
                )
            }
        }
    }
}