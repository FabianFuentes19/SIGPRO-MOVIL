package com.sigpro.lider.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sigpro.lider.ui.theme.GrisTextoSecundario
import com.sigpro.lider.ui.theme.NegroTexto

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = GrisTextoSecundario, fontSize = 14.sp)
        Text(text = value, fontWeight = FontWeight.Bold, color = NegroTexto, fontSize = 14.sp)
    }
}