package com.sigpro.lider.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sigpro.lider.ui.theme.BlancoPuro
import com.sigpro.lider.ui.theme.VerdeExito

@Composable
fun SeccionHeader(titulo: String, icono: ImageVector) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(VerdeExito, RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icono, contentDescription = null, tint = BlancoPuro, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = titulo, color = BlancoPuro, fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }
}