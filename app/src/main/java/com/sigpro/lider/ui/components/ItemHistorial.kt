package com.sigpro.lider.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sigpro.lider.ui.theme.GrisTextoSecundario
import com.sigpro.lider.ui.theme.NegroTexto
import com.sigpro.lider.ui.theme.VerdeExito

@Composable
fun ItemHistorial(titulo: String, fecha: String, monto: String) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = 1.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Outlined.Payments, contentDescription = null, tint = NegroTexto)
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(text = titulo, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text(text = fecha, color = GrisTextoSecundario, fontSize = 12.sp)
                }
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(text = monto, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = "COMPLETADO", color = VerdeExito, fontWeight = FontWeight.Bold, fontSize = 10.sp)
            }
        }
    }
}