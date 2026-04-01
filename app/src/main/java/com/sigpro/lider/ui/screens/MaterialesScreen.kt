package com.sigpro.lider.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sigpro.lider.models.MaterialModel
import com.sigpro.lider.ui.components.AgregarMaterialDialog
import com.sigpro.lider.ui.theme.*
import com.sigpro.lider.viewmodel.MaterialViewModel
import java.text.NumberFormat
import java.util.*

@Composable
fun MaterialesScreen(
    onBack: () -> Unit,
    viewModel: MaterialViewModel = viewModel()
) {
    var showAgregarDialog by remember { mutableStateOf(false) }
    var searchTexto by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.fetchMateriales(proyectoId = 1L)
    }

    Scaffold(
        backgroundColor = GrisFondoApp,
        topBar = {
            TopAppBar(
                title = { Text("Gestión de Materiales", color = BlancoPuro) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar", tint = BlancoPuro)
                    }
                },
                backgroundColor = AzulPrimario,
                elevation = 0.dp
            )
        },
    ) { padding ->
        if (viewModel.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = AzulPrimario)
            }
        }

        if (showAgregarDialog) {
            AgregarMaterialDialog(
                onDismiss = { showAgregarDialog = false },
                onRegistrar = { nombre, cantStr, precioStr ->
                    val cantInt = cantStr.toIntOrNull() ?: 0
                    val precioDouble = precioStr.toDoubleOrNull() ?: 0.0

                    if (nombre.isNotBlank() && cantInt > 0) {
                        val nuevo = MaterialModel(
                            nombre = nombre,
                            cantidad = cantInt,
                            monto = precioDouble,
                            proyectoId = 1L
                        )

                        viewModel.registrarNuevoMaterial(nuevo) {
                            showAgregarDialog = false
                        }
                    }
                }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Materiales",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = NegroTexto
                )

                FloatingActionButton(
                    onClick = { showAgregarDialog = true },
                    backgroundColor = VerdeExito,
                    contentColor = BlancoPuro,
                    modifier = Modifier.size(56.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar", modifier = Modifier.size(30.dp))
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            val totalCalculado = viewModel.listaMateriales.sumOf { (it.monto * it.cantidad) }

            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = 0.dp,
                backgroundColor = BlancoPuro,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        text = "TOTAL EN MATERIALES:",
                        fontSize = 12.sp,
                        color = GrisTextoSecundario,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = formatCurrency(totalCalculado),
                        fontSize = 32.sp,
                        color = NegroTexto,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = searchTexto,
                onValueChange = { searchTexto = it },
                placeholder = { Text("Buscar materiales...", color = GrisTextoSecundario) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = GrisTextoSecundario) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = BlancoPuro,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = AzulPrimario
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            val listaFiltrada = viewModel.listaMateriales.filter {
                it.nombre.contains(searchTexto, ignoreCase = true)
            }

            if (listaFiltrada.isEmpty() && !viewModel.isLoading) {
                Text(
                    text = "No hay materiales registrados.",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = GrisTextoSecundario
                )
            }

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(listaFiltrada) { material ->
                    CardMaterialItem(material)
                }
                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }
    }
}

@Composable
fun CardMaterialItem(material: MaterialModel) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = 1.dp,
        backgroundColor = BlancoPuro,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = material.nombre,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = NegroTexto
                )
                Text(
                    text = "${material.cantidad} unidades",
                    fontSize = 12.sp,
                    color = GrisTextoSecundario
                )
            }
            Text(
                text = formatCurrency(material.monto),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = NegroTexto
            )
        }
    }
}

fun formatCurrency(amount: Double): String {
    val format = NumberFormat.getCurrencyInstance(Locale("es", "MX"))
    return format.format(amount)
}
