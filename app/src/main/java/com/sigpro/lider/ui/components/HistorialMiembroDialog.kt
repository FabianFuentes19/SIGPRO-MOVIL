package com.sigpro.lider.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.sigpro.lider.models.VoucherDTO
import java.util.Locale

@Composable
fun HistorialMiembroDialog(
    nombre: String,
    puesto: String,
    totalAcumulado: String,
    listaVouchers: List<VoucherDTO>,
    cargando: Boolean,
    onDismiss: () -> Unit
) {
    val azulMarino = Color(0xFF00334E)
    val tealGreen = Color(0xFF00897B)
    val grisLabel = Color(0xFF94A3B8)

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxSize()
            ) {
                // Título Principal
                Text(
                    text = "Historial",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = azulMarino,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // Sección de Perfil
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Avatar Circular
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .background(tealGreen, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(16.dp))
                    
                    Column {
                        Text(
                            text = nombre,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            text = puesto,
                            fontSize = 14.sp,
                            color = grisLabel
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Total acumulado",
                            fontSize = 12.sp,
                            color = grisLabel
                        )
                        Text(
                            text = totalAcumulado,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = tealGreen
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                Divider(color = Color(0xFFF1F5F9), thickness = 1.dp)
                Spacer(modifier = Modifier.height(24.dp))

                // Historial de pagos
                Text(
                    text = "Historial de pagos",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = azulMarino,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Box(modifier = Modifier.fillMaxWidth().weight(1f)) {
                    if (cargando) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = tealGreen
                        )
                    } else if (listaVouchers.isEmpty()) {
                        Text(
                            text = "No hay registros de pagos aún",
                            color = grisLabel,
                            modifier = Modifier.align(Alignment.Center),
                            textAlign = TextAlign.Center
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            contentPadding = PaddingValues(bottom = 16.dp)
                        ) {
                            items(listaVouchers) { voucher ->
                                CardPago(voucher)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botón Cerrar
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, tealGreen),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = tealGreen)
                    ) {
                        Text(
                            text = "Cerrar",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CardPago(voucher: VoucherDTO) {
    val grisLabel = Color(0xFF94A3B8)
    
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color(0xFFF1F5F9), RoundedCornerShape(16.dp))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Izquierda: Fecha
                Column {
                    Text(
                        text = "FECHA DE PAGO",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = grisLabel
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = voucher.fechaPagoReal ?: voucher.fechaFin,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

                // Derecha: Monto
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "MONTO",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = grisLabel
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "$${String.format(Locale.US, "%,.2f", if (voucher.estado == "PAGADO") (voucher.montoPagado ?: 0.0) else voucher.montoEsperado)}",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }
        }
    }
}
