package com.sigpro.lider.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange // IMPORTANTE
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sigpro.lider.models.ProyectoResponseDTO

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProyectoCard(
    proyecto: ProyectoResponseDTO,
    onClick: () -> Unit
) {
    // calculo
    val presupuestoTotal = if (proyecto.presupuestoAutorizado > 0)
        proyecto.presupuestoAutorizado else proyecto.presupuestoInicial

    val porcentajeRestante = if (presupuestoTotal > 0)
        (proyecto.presupuesto / presupuestoTotal).toFloat() else 0f

    // Colores de barras
    val colorBarraAlerta = when {
        porcentajeRestante <= 0.10f -> Color(0xFFD32F2F)
        porcentajeRestante <= 0.20f -> Color(0xFFFFD600)
        else -> Color(0xFF00897B)
    }

    val (colorEstado, mensajeAlerta) = when {
        proyecto.presupuesto <= 0 -> Color(0xFFD32F2F) to "Presupuesto agotado"
        porcentajeRestante <= 0.10f -> Color(0xFFD32F2F) to "Te queda menos del 10% de presupuesto"
        porcentajeRestante <= 0.20f -> Color(0xFFF57C00) to "Te queda menos del 20% de presupuesto"
        else -> Color(0xFF00897B) to null
    }
    val colorTextoAlerta = Color(0xFF212121)

    val colorDisenoFijo = Color(0xFF00897B)

    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        elevation = 4.dp
    ) {
        Column {
            // Banner superior: SIEMPRE VERDE (como pediste)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(colorDisenoFijo)
            )

            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = proyecto.nombre,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF00334E)
                )

                Text(
                    text = proyecto.objetivoGeneral,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF1F4F9), shape = RoundedCornerShape(8.dp))
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(Color(0xFFE0F2F1), shape = RoundedCornerShape(6.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = null,
                            tint = colorDisenoFijo,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("FECHA", fontSize = 10.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                        Text("${proyecto.fechaInicio} — ${proyecto.fechaFin}", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("Restante", fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                Text(
                    text = "$${String.format("%,.2f", proyecto.presupuesto)}",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF003D61)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Progreso
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Estado del presupuesto", fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                    Text(
                        "${(porcentajeRestante * 100).toInt()}%",
                        fontSize = 12.sp,
                        color = colorBarraAlerta,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                LinearProgressIndicator(
                    progress = porcentajeRestante.coerceIn(0f, 1f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp),
                    color = colorBarraAlerta,
                    backgroundColor = Color(0xFFE0E0E0)
                )

                mensajeAlerta?.let {
                    Text(
                        text = it,
                        color = colorTextoAlerta,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    }
}