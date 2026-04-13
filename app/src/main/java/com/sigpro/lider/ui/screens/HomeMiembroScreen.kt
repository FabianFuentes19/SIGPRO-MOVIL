package com.sigpro.lider.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Password
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
import androidx.navigation.compose.currentBackStackEntryAsState
import com.sigpro.lider.session.SessionManager
import com.sigpro.lider.ui.components.CambiarContrasenaDialog
import com.sigpro.lider.ui.components.LogoutDialog
import com.sigpro.lider.ui.components.SeccionHeader
import com.sigpro.lider.ui.theme.*
import com.sigpro.lider.viewmodel.HomeMiembroViewModel

@Composable
fun HomeMiembroScreen(
    navController: NavController,
    viewModel: HomeMiembroViewModel = viewModel()
) {

    val context = LocalContext.current
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var showLogoutDialog by remember { mutableStateOf(false) }
    var showCambiarPasswordDialog by remember { mutableStateOf(false) }

    val usuario = viewModel.miembroDetallado
    val proyecto = viewModel.proyecto
    val cargando = viewModel.cargando

    val azulMarino = Color(0xFF00334E)
    val verdeTurquesa = Color(0xFF009688)

    LaunchedEffect(Unit) {
        viewModel.cargarProyecto()
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

    if (showCambiarPasswordDialog) {
        CambiarContrasenaDialog(
            onDismiss = {
                showCambiarPasswordDialog = false
                viewModel.mensajePassword = null
            },
            onGuardar = { actual, nueva ->
                viewModel.actualizarContrasena(actual, nueva) {
                    showCambiarPasswordDialog = false
                    android.widget.Toast.makeText(
                        context,
                        "Cambio de contraseña exitoso",
                        android.widget.Toast.LENGTH_SHORT
                    ).show()
                }
            },
            cargando = viewModel.cargandoPassword,
            errorDesdeServidor = viewModel.mensajePassword
        )
    }
    Scaffold(
        backgroundColor = Color(0xFFF5F5F5),
        topBar = {
            TopAppBar(
                title = { Text("Perfil", color = BlancoPuro, fontWeight = FontWeight.Bold) },
                backgroundColor = AzulPrimario,
                actions = {
                    IconButton(onClick = { showLogoutDialog = true }) {
                        Icon(
                            Icons.Default.ExitToApp,
                            contentDescription = "Cerrar",
                            tint = BlancoPuro
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigation(
                backgroundColor = azulMarino,
                contentColor = Color.White,
                elevation = 8.dp
            ) {
                BottomNavigationItem(
                    icon = {
                        Icon(
                            Icons.Default.Payments,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = { Text(text = "Nóminas", fontSize = 12.sp) },
                    selected = currentRoute == "nominas_miembro",
                    onClick = {
                        if (currentRoute != "nominas_miembro") {
                            navController.navigate("nominas_miembro") {
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    alwaysShowLabel = true
                )

                BottomNavigationItem(
                    icon = {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = { Text(text = "Perfil", fontSize = 12.sp) },
                    selected = currentRoute == "home_miembro",
                    onClick = {
                        if (currentRoute != "home_miembro") {
                            navController.navigate("home_miembro") {
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    alwaysShowLabel = true
                )
            }
        }
    ) { paddingValues ->
        if (cargando || usuario == null) {
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = AzulPrimario)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    elevation = 4.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Surface(color = verdeTurquesa, modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.Person,
                                    contentDescription = null,
                                    tint = BlancoPuro
                                )
                                Spacer(Modifier.width(8.dp))
                                Text("Mi Perfil", color = BlancoPuro, fontWeight = FontWeight.Bold)
                            }
                        }
                        Column(modifier = Modifier.padding(16.dp)) {
                            InfoLabel(
                                label = "NOMBRE",
                                value = usuario.nombreCompleto ?: "No disponible"
                            )
                            Spacer(Modifier.height(12.dp))
                            Row(Modifier.fillMaxWidth()) {
                                Box(Modifier.weight(1f)) {
                                    InfoLabel(
                                        label = "MATRÍCULA",
                                        value = usuario.matricula ?: "-"
                                    )
                                }
                                Box(Modifier.weight(1f)) {
                                    InfoLabel(
                                        label = "GRUPO",
                                        value = usuario.grupo ?: "-"
                                    )
                                }
                            }
                            Spacer(Modifier.height(12.dp))
                            InfoLabel(label = "CARRERA", value = usuario.carrera ?: "No asignada")
                            Spacer(Modifier.height(12.dp))
                            Text(
                                "PUESTO",
                                fontSize = 11.sp,
                                color = Color.Gray,
                                fontWeight = FontWeight.Bold
                            )
                            Surface(
                                color = Color(0xFFE0F2F1),
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier.padding(top = 4.dp)
                            ) {
                                Text(
                                    usuario.puesto ?: "General",
                                    modifier = Modifier.padding(
                                        horizontal = 12.dp,
                                        vertical = 4.dp
                                    ),
                                    color = azulMarino,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Card(
                    shape = RoundedCornerShape(12.dp),
                    elevation = 4.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Surface(color = azulMarino, modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.Assignment,
                                    contentDescription = null,
                                    tint = BlancoPuro
                                )
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    "Proyecto asignado",
                                    color = BlancoPuro,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        if (viewModel.errorMensaje != null) {
                            Text(
                                text = viewModel.errorMensaje!!,
                                color = Color.Red,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                        Column(modifier = Modifier.padding(16.dp)) {
                            if (proyecto != null) {
                                InfoLabel(
                                    label = "NOMBRE PROYECTO",
                                    value = proyecto.nombre ?: "Sin nombre"
                                )
                                Spacer(Modifier.height(12.dp))
                                Row(Modifier.fillMaxWidth()) {
                                    Box(Modifier.weight(1f)) {
                                        InfoLabel(
                                            label = "FECHA INICIO",
                                            value = proyecto.fechaInicio ?: "-"
                                        )
                                    }
                                    Box(Modifier.weight(1f)) {
                                        InfoLabel(
                                            label = "FECHA FIN",
                                            value = proyecto.fechaFin ?: "-"
                                        )
                                    }
                                }
                                Spacer(Modifier.height(12.dp))
                                InfoLabel(
                                    label = "OBJETIVO",
                                    value = proyecto.objetivoGeneral ?: "No especificado"
                                )
                                Spacer(Modifier.height(12.dp))
                                InfoLabel(
                                    label = "DESCRIPCIÓN",
                                    value = proyecto.descripcion ?: "Sin descripción"
                                )
                            }
                                else if (!cargando) {
                                    Text(
                                        "No tienes un proyecto asignado.",
                                        color = Color.Gray,
                                        fontSize = 14.sp
                                    )
                                }
                            }
                        }
                    }

                Spacer(modifier = Modifier.height(20.dp))

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
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
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
                            onClick = { showCambiarPasswordDialog = true },
                            colors = ButtonDefaults.textButtonColors(contentColor = AzulPrimario)
                        ) {
                            Text("CAMBIAR", fontWeight = FontWeight.ExtraBold)
                            Icon(Icons.Default.ChevronRight, contentDescription = null)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}


@Composable
fun InfoLabel(label: String, value: String) {
    Column {
        Text(text = label, fontSize = 11.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
        Text(text = value, fontSize = 15.sp, color = Color.Black, fontWeight = FontWeight.SemiBold)
    }
}