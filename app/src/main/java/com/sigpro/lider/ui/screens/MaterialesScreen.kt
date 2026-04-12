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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.sigpro.lider.models.MaterialResponseDTO
import com.sigpro.lider.ui.components.AgregarMaterialDialog
import com.sigpro.lider.ui.theme.*
import com.sigpro.lider.viewmodel.MaterialesViewModel

@Composable
fun MaterialesScreen(
    navController: NavController,
    proyectoId: Long,
    viewModel: MaterialesViewModel = viewModel()
) {
    val context = LocalContext.current
    var showAgregarDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.cargarMateriales(proyectoId)
    }

    Scaffold(
        backgroundColor = GrisFondoApp,
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar", tint = BlancoPuro)
                    }
                },
                backgroundColor = AzulPrimario,
                elevation = 0.dp
            )
        }
    ) { padding ->
        if (showAgregarDialog) {
            AgregarMaterialDialog(
                onDismiss = { showAgregarDialog = false },
                onRegistrar = { nombre, cantidad, precio ->
                    val cantInt = cantidad.toIntOrNull() ?: 0
                    val precioDouble = precio.toDoubleOrNull() ?: 0.0
                    viewModel.registrarNuevoMaterial(
                        context = context,
                        nombre = nombre,
                        cantidad = cantInt,
                        monto = precioDouble,
                        proyectoId = proyectoId
                    )

                    showAgregarDialog = false
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
                    elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 4.dp),
                    modifier = Modifier.size(56.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar", modifier = Modifier.size(30.dp))
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = 0.dp,
                backgroundColor = BlancoPuro,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        text = "TOTAL DE MATERIAL:",
                        fontSize = 12.sp,
                        color = GrisTextoSecundario,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "$${String.format("%,.2f", viewModel.totalInvertido)}",
                        fontSize = 32.sp,
                        color = NegroTexto,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = viewModel.textoBusqueda,
                onValueChange = { viewModel.textoBusqueda = it },
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

            if (viewModel.cargando) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = AzulPrimario)
                }
            } else if (viewModel.materialesFiltrados.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = GrisTextoSecundario.copy(alpha = 0.5f)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Material no encontrado",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = GrisTextoSecundario
                        )
                        Text(
                            text = "Intenta con otro nombre o palabra clave",
                            fontSize = 14.sp,
                            color = GrisTextoSecundario.copy(alpha = 0.7f)
                        )
                    }
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(viewModel.materialesFiltrados) { material ->
                        CardMaterialItem(material)
                    }
                    item { Spacer(modifier = Modifier.height(80.dp)) }
                }
            }
        }
    }
}

@Composable
fun CardMaterialItem(material: MaterialResponseDTO) {
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
                    text = "${material.cantidad} pz",
                    fontSize = 12.sp,
                    color = GrisTextoSecundario
                )
            }
            Text(
                text = "$${String.format("%,.2f", material.costoTotal)}",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = NegroTexto
            )
        }
    }
}