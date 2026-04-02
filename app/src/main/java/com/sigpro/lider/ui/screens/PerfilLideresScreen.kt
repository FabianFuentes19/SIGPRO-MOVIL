package com.sigpro.lider.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.sigpro.lider.ui.components.*
import com.sigpro.lider.ui.theme.*

@Composable
fun PerfilLideresScreen(navController: NavController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val azulMarino = Color(0xFF00334E)
    var showLogoutDialog by remember { mutableStateOf(false) }

    if (showLogoutDialog) {
        LogoutDialog(
            onDismiss = { showLogoutDialog = false },
            onConfirm = {
                showLogoutDialog = false

                navController.navigate("login") {
                    popUpTo("home") { inclusive = true }
                    launchSingleTop = true
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
                        Icon(Icons.Default.ExitToApp, contentDescription = "Cerrar Sesión", tint = BlancoPuro)
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
                    icon = { Icon(Icons.Default.Home, contentDescription = null, modifier = Modifier.size(24.dp)) },
                    label = { Text(text = "Inicio", fontSize = 12.sp) },
                    selected = currentRoute == "home",
                    onClick = {
                        navController.navigate("home") {
                            popUpTo("home") {
                                inclusive = true
                            }
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
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            SeccionHeader(titulo = "Información Personal", icono = Icons.Default.Person)
            Card(
                shape = RoundedCornerShape(12.dp),
                elevation = 2.dp,
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    InfoRow(label = "Nombre", value = "Zuri Rodriguez")
                    InfoRow(label = "Matrícula", value = "20210345")
                    InfoRow(label = "Cuatrimestre", value = "7")
                    InfoRow(label = "Carrera", value = "Diseño Gráfico Digital")
                    InfoRow(label = "Puesto", value = "Líder")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            SeccionHeader(titulo = "Historial de Pagos", icono = Icons.Outlined.Payments)
            Spacer(modifier = Modifier.height(8.dp))

            ItemHistorial(titulo = "Nómina Quincenal", fecha = "15 Oct 2023", monto = "$1,200.00")
            Spacer(modifier = Modifier.height(8.dp))
            ItemHistorial(titulo = "Nómina Quincenal", fecha = "30 Sep 2023", monto = "$1,200.00")
            Spacer(modifier = Modifier.height(8.dp))
            ItemHistorial(titulo = "Nómina Quincenal", fecha = "15 Sep 2023", monto = "$1,200.00")
        }
    }
}
/*
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PerfilPreview() {
    SigproTheme {
        PerfilLideresScreen(onNavigateToLogin = {})
    }
}*/