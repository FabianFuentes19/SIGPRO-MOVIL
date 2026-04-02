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
    val colorTextoPrincipal = if (nomina.isPagado) Color(0xFF9E9E9E) else NegroTexto
    val colorTextoSecundario = if (nomina.isPagado) Color(0xFFBDBDBD) else GrisTextoSecundario
    val colorMonto = if (nomina.isPagado) Color(0xFFBDBDBD) else AzulPrimario

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
                    Text(text = nomina.monto, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = colorMonto)
                }

                val badgeColor = if (nomina.isPagado) Color(0xFFE0E0E0) else Color(0xFFFFFDE7)
                val badgeTextColor = if (nomina.isPagado) Color(0xFF616161) else Color(0xFFFBC02D)

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

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Icon(Icons.Outlined.CalendarToday, contentDescription = null, tint = colorTextoSecundario, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = nomina.fecha, fontSize = 12.sp, color = colorTextoSecundario)
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (!nomina.isPagado) {
                Button(
                    onClick = onPagarClick,
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = VerdeExito)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.Money, contentDescription = null, tint = BlancoPuro)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Pagar Nómina", color = BlancoPuro, fontWeight = FontWeight.Bold)
                    }
                }
            } else {
                Surface(
                    color = Color(0xFFF5F5F5),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Outlined.CheckCircleOutline, contentDescription = null, tint = Color(0xFFBDBDBD))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Pagado con éxito", color = Color(0xFFBDBDBD), fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}