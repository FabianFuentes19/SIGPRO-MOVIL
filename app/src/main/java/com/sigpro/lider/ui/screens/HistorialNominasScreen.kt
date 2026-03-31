package com.sigpro.lider.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// AQUÍ IMPORTA TU COMPONENTE SEPARADO
import com.sigpro.lider.ui.components.CardNominaItem
import com.sigpro.lider.ui.theme.*

// MODELO DE DATOS
data class NominaData(
    val id: Int,
    val nombre: String,
    val puesto: String,
    val voucher: String,
    val monto: String,
    val fecha: String,
    var isPagado: Boolean = false
)

@Composable
fun HistorialNominasScreen() {
    var searchText by remember { mutableStateOf("") }
    var filtroSeleccionado by remember { mutableStateOf("Todos") }

    val listaNominas = remember {
        mutableStateListOf(
            NominaData(1, "Marcos Ortíz", "Diseñador gráfico", "00123", "$15,000", "15/10/2023", isPagado = false),
            NominaData(2, "Roberto Sánchez", "Ingeniero Civil", "00119", "$18,000", "15/10/2023", isPagado = true),
            NominaData(3, "Zuri Rodriguez", "Programadora", "00124", "$15,000", "15/10/2023", isPagado = false),
            NominaData(4, "Roberto Fuentes", "Diseñador", "00120", "$16,000", "14/10/2023", isPagado = true)
        )
    }

    val nominasFiltradas = when (filtroSeleccionado) {
        "Pendientes" -> listaNominas.filter { !it.isPagado }
        "Pagados" -> listaNominas.filter { it.isPagado }
        else -> listaNominas
    }

    Scaffold(
        backgroundColor = GrisFondoApp,
        topBar = {
            TopAppBar(
                title = { Text("Nóminas", color = BlancoPuro, fontWeight = FontWeight.Bold, fontSize = 20.sp) },
                backgroundColor = AzulPrimario,
                elevation = 0.dp,
                modifier = Modifier.height(80.dp)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // BUSCADOR
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = { Text("Buscar por nombre o puesto...", color = GrisTextoSecundario) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = GrisTextoSecundario) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = BlancoPuro,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = AzulPrimario
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // BOTONES DE FILTRADO
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val opciones = listOf("Todos", "Pendientes", "Pagados")
                opciones.forEach { opcion ->
                    BotonFiltro(
                        texto = opcion,
                        isSelected = filtroSeleccionado == opcion,
                        onClick = { filtroSeleccionado = opcion }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // LISTA DE NÓMINAS
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(nominasFiltradas) { nomina ->
                    // USANDO EL COMPONENTE SEPARADO
                    CardNominaItem(
                        nomina = nomina,
                        onPagarClick = {
                            val index = listaNominas.indexOfFirst { it.id == nomina.id }
                            if (index != -1) {
                                listaNominas[index] = listaNominas[index].copy(isPagado = true)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun BotonFiltro(texto: String, isSelected: Boolean, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        backgroundColor = if (isSelected) AzulPrimario else BlancoPuro,
        elevation = if (isSelected) 2.dp else 0.dp,
        modifier = Modifier
            .width(110.dp)
            .height(40.dp)
            .clickable { onClick() }
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = texto,
                color = if (isSelected) BlancoPuro else GrisTextoSecundario,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                fontSize = 14.sp
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NominasScreenPreview() {
    // Aquí envolvemos la pantalla en tu tema para que use los colores correctos
    SigproTheme {
        HistorialNominasScreen()
    }
}