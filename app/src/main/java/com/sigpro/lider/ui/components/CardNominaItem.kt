package com.sigpro.lider.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.CheckCircleOutline
import androidx.compose.material.icons.outlined.Money
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sigpro.lider.ui.screens.NominaData
import com.sigpro.lider.ui.theme.*
@Composable
fun CardNominaItem(
    nomina: NominaData,
    onPagarClick: () -> Unit
) {
    val fondoCard = BlancoPuro
    val colorTextoPrincipal = NegroTexto
    val colorTextoSecundario = GrisTextoSecundario
    val colorMonto = AzulPrimario

    Card(
        shape = RoundedCornerShape(24.dp),
        backgroundColor = fondoCard,
        elevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text(text = nomina.nombre, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = colorTextoPrincipal)
                    Text(text = nomina.puesto, fontSize = 14.sp, color = colorTextoSecundario)
                }
                Text(
                    text = "NO. VOUCHER: ${nomina.voucher}",
                    fontSize = 11.sp,
                    color = colorTextoSecundario,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = if (nomina.isPagado) "MONTO PAGADO" else "TOTAL A PAGAR",
                        fontSize = 11.sp,
                        color = colorTextoSecundario,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = "$${nomina.monto}", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = colorMonto)
                }

                val badgeColor = if (nomina.isPagado) Color(0xFFE0F2F1) else Color(0xFFFFFDE7)
                val badgeTextColor = if (nomina.isPagado) Color(0xFF00897B) else Color(0xFFFBC02D)

                Surface(
                    color = badgeColor,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = if (nomina.isPagado) "PAGADO" else "PENDIENTE",
                        color = badgeTextColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }

            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Fecha pago",
                        fontSize = 10.sp,
                        color = colorTextoSecundario,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 2.dp)
                    )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Outlined.CalendarToday,
                        contentDescription = null,
                        tint = colorTextoSecundario,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = nomina.fecha, fontSize = 12.sp, color = colorTextoSecundario)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // --- BOTÓN DESHABILITADO VS ACTIVO ---
            Button(
                onClick = { if (!nomina.isPagado) onPagarClick() },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp),
                // Si está pagado, usamos un gris claro, si no, el VerdeExito
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (nomina.isPagado) Color(0xFFF5F5F5) else VerdeExito,
                    disabledBackgroundColor = Color(0xFFF5F5F5)
                ),
                enabled = !nomina.isPagado, // Deshabilita el click
                elevation = if (nomina.isPagado) ButtonDefaults.elevation(0.dp) else ButtonDefaults.elevation(2.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (nomina.isPagado) {
                        Icon(Icons.Outlined.CheckCircleOutline, contentDescription = null, tint = Color(0xFFBDBDBD))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Pagado con éxito", color = Color(0xFFBDBDBD), fontWeight = FontWeight.Bold)
                    } else {
                        Icon(Icons.Outlined.Money, contentDescription = null, tint = BlancoPuro)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Pagar Nómina", color = BlancoPuro, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}