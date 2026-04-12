package com.sigpro.lider.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material.icons.outlined.Payments
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
import com.sigpro.lider.ui.components.*
import com.sigpro.lider.ui.theme.*
import com.sigpro.lider.viewmodel.PerfilViewModel
import com.sigpro.lider.session.SessionManager

@Composable
fun PerfilLideresScreen(
    navController: NavController,
    viewModel: PerfilViewModel = viewModel()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val azulMarino = Color(0xFF00334E)
    var showLogoutDialog by remember { mutableStateOf(false) }

    val listaPagos by viewModel.pagos.collectAsState()
    val usuario = viewModel.usuario
    val cargando = viewModel.cargando

    var showCambiarPasswordDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val matricula = SessionManager.getMatricula()
        if (!matricula.isNullOrBlank()) {
            viewModel.cargarPerfil(matricula)
            viewModel.cargarHistorialPagos()
        }
    }

    if (showLogoutDialog) {
        LogoutDialog(
            onDismiss = { showLogoutDialog = false },
            onConfirm = {
                showLogoutDialog = false
                navController.navigate("login") {
                    popUpTo("home") { inclusive = true }
                }
            }
        )
    }

    Scaffold(
        backgroundColor = GrisFondoApp,
        topBar = {
            TopAppBar(
                title = { Text("Perfil", color = BlancoPuro, fontWeight = FontWeight.Bold) },
                backgroundColor = AzulPrimario,
                actions = {
                    IconButton(onClick = { showLogoutDialog = true }) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Cerrar", tint = BlancoPuro)
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigation(backgroundColor = azulMarino, contentColor = Color.White) {
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text("Inicio") },
                    selected = currentRoute == "home",
                    onClick = { navController.navigate("home") }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Payments, contentDescription = null) },
                    label = { Text("Nóminas") },
                    selected = currentRoute == "nominas",
                    onClick = { navController.navigate("nominas") }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = null) },
                    label = { Text("Perfil") },
                    selected = currentRoute == "perfil",
                    onClick = {  }
                )
            }
        }
    ) { padding ->
        if (cargando) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = AzulPrimario)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
            ) {
                item {
                    SeccionHeader(titulo = "Información Personal", icono = Icons.Default.Person)
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        elevation = 2.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            InfoRow(label = "Nombre", value = usuario?.nombreCompleto ?: "No disponible")
                            InfoRow(label = "Matrícula", value = usuario?.matricula ?: "-")
                            InfoRow(label = "Cuatrimestre", value = usuario?.cuatrimestre?.toString() ?: "-")
                            InfoRow(label = "Carrera", value = usuario?.carrera ?: "-")
                            InfoRow(label = "Puesto", value = usuario?.puesto ?: "-")
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    SeccionHeader(titulo = "Seguridad", icono = Icons.Outlined.Lock)
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        elevation = 2.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                Icon(
                                    imageVector = Icons.Outlined.Password,
                                    contentDescription = null,
                                    tint = AzulPrimario
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = "Actualiza tu contraseña",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = NegroTexto
                                    )
                                    Text(
                                        text = "Cambia tu clave de acceso",
                                        color = Color.Gray,
                                        fontSize = 11.sp
                                    )
                                }
                            }
                            TextButton(
                                onClick = {
                                    showCambiarPasswordDialog = true
                                },
                                colors = ButtonDefaults.textButtonColors(contentColor = AzulPrimario)
                            ) {
                                Text("CAMBIAR", fontWeight = FontWeight.ExtraBold)
                                Icon(Icons.Default.ChevronRight, contentDescription = null)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

                item {
                    SeccionHeader(titulo = "Historial de Pagos", icono = Icons.Outlined.Payments)
                    Spacer(modifier = Modifier.height(8.dp))
                }

                if (listaPagos.isEmpty()) {
                    item {
                        Text(
                            text = "No hay pagos registrados",
                            modifier = Modifier.padding(16.dp),
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    }
                } else {
                    items(listaPagos) { pago ->
                        ItemHistorial(
                            titulo = "Nómina Quincenal",
                            fecha = pago.fecha,
                            monto = "$${pago.monto}"
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }

    if (showCambiarPasswordDialog) {
        CambiarContrasenaDialog(
            onDismiss = { showCambiarPasswordDialog = false },
            onGuardar = { actual, nueva ->
                showCambiarPasswordDialog = false
            }
        )
    }
}