package com.sigpro.lider.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.sigpro.lider.ui.components.CardNominaItem
import com.sigpro.lider.ui.theme.*
import com.sigpro.lider.viewmodels.PagosViewModel
import java.math.BigDecimal
data class NominaData(
    val id: Int,
    val nombre: String,
    val matricula: String,
    val puesto: String,
    val voucher: String,
    val monto: BigDecimal,
    val fecha: String,
    var isPagado: Boolean = false
)

@Composable
fun HistorialNominasScreen(
    navController: NavController,
    viewModel: PagosViewModel = viewModel()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var searchText by remember { mutableStateOf("") }
    var filtroSeleccionado by remember { mutableStateOf("Todos") }

    LaunchedEffect(Unit) {
        viewModel.cargarNominas()
    }

    val listaNominas = viewModel.listaNominas
    val estaCargando = viewModel.cargando

    val nominasFiltradas = listaNominas.filter { nomina ->
        val coincideBusqueda = nomina.nombre.contains(searchText, ignoreCase = true) ||
                nomina.puesto.contains(searchText, ignoreCase = true)

        val coincideEstado = when (filtroSeleccionado) {
            "Pendientes" -> !nomina.isPagado
            "Pagados" -> nomina.isPagado
            else -> true
        }
        coincideBusqueda && coincideEstado
    }

    val azulMarino = Color(0xFF00334E)

    Scaffold(
        backgroundColor = GrisFondoApp,
        topBar = {
            TopAppBar(
                title = { Text("Nóminas", color = BlancoPuro, fontWeight = FontWeight.Bold) },
                backgroundColor = AzulPrimario
            )
        },
        bottomBar = {
            BottomNavigation(
                backgroundColor = azulMarino,
                contentColor = Color.White,
                elevation = 8.dp
            ) {
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = null, modifier = Modifier.size(24.dp)) },
                    label = { Text(text = "Inicio", fontSize = 12.sp) },
                    selected = currentRoute == "home",
                    onClick = {
                        navController.navigate("home") {
                            popUpTo("home") { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    alwaysShowLabel = true
                )

                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Payments, contentDescription = null, modifier = Modifier.size(24.dp)) },
                    label = { Text(text = "Nóminas", fontSize = 12.sp) },
                    selected = currentRoute == "nominas",
                    onClick = {
                        if (currentRoute != "nominas") {
                            navController.navigate("nominas") {
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    alwaysShowLabel = true
                )

                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(24.dp)) },
                    label = { Text(text = "Perfil", fontSize = 12.sp) },
                    selected = currentRoute == "perfil",
                    onClick = {
                        if (currentRoute != "perfil") {
                            navController.navigate("perfil") {
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    alwaysShowLabel = true
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

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

            // Botones de Filtro
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

            if (estaCargando) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = AzulPrimario, strokeWidth = 4.dp)
                }
            } else {
                if (nominasFiltradas.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No se encontraron registros", color = GrisTextoSecundario)
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        items(nominasFiltradas) { nomina ->
                            CardNominaItem(
                                nomina = nomina,
                                onPagarClick = {
                                    viewModel.registrarPago(nomina)
                                }
                            )
                        }
                    }
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