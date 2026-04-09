package com.sigpro.lider.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sigpro.lider.models.ProyectoResponseDTO
import com.sigpro.lider.models.UsuarioDTO
import com.sigpro.lider.ui.components.MiembroInfoCard
import com.sigpro.lider.ui.theme.*

@Composable
fun HomeMiembroScreen(usuario: UsuarioDTO, proyecto: ProyectoResponseDTO) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        MiembroInfoCard(
            titulo = "Mi Perfil",
            icono = Icons.Default.Person,
            colorFondo = VerdeExito
        ) {
            DatoEtiqueta(etiqueta = "NOMBRE", valor = usuario.nombreCompleto)

            Row(Modifier.fillMaxWidth()) {
                Box(Modifier.weight(1f)) {
                    DatoEtiqueta("MATRÍCULA", usuario.matricula)
                }
                Box(Modifier.weight(1f)) {
                    DatoEtiqueta("CUATRIMESTRE", "${usuario.cuatrimestre}mo")
                }
            }

            DatoEtiqueta("CARRERA", usuario.carrera ?: "Ingeniería en Tecnologías")

            Spacer(modifier = Modifier.height(8.dp))

            Surface(
                color = GrisSuaveCaja,
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = usuario.rolNombre ?: "Programador",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    color = AzulPrimario,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        MiembroInfoCard(
            titulo = "Proyecto asignado",
            icono = Icons.Default.Assignment,
            colorFondo = AzulPrimario
        ) {
            DatoEtiqueta("NOMBRE PROYECTO", proyecto.nombre)

            Row(Modifier.fillMaxWidth()) {
                Box(Modifier.weight(1f)) {
                    DatoEtiqueta("FECHA INICIO", proyecto.fechaInicio)
                }
                Box(Modifier.weight(1f)) {
                    DatoEtiqueta("FECHA FIN", proyecto.fechaFin)
                }
            }

            DatoEtiqueta("LIDER", "Juan Pérez")
            DatoEtiqueta("OBJETIVO", proyecto.objetivoGeneral)

            Text(
                text = "DESCRIPCIÓN",
                fontSize = 10.sp,
                color = VerdeExito,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = proyecto.descripcion,
                fontSize = 14.sp,
                color = GrisTextoSecundario,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
fun DatoEtiqueta(etiqueta: String, valor: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = etiqueta,
            fontSize = 10.sp,
            color = VerdeExito,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = valor,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = NegroTexto
        )
    }
}