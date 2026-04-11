package com.sigpro.lider.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.sigpro.lider.ui.theme.*

@Composable
fun HomeMiembroScreen(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val azulMarino = Color(0xFF00334E)
    val verdeTurquesa = Color(0xFF009688)

    Scaffold(
        backgroundColor = Color(0xFFF5F5F5),
        topBar = {
            TopAppBar(
                title = { Text("Perfil", color = BlancoPuro, fontWeight = FontWeight.Bold) },
                backgroundColor = AzulPrimario,
                actions = {
                    IconButton(onClick = {
                        navController.navigate("login") {
                            popUpTo(0) { inclusive = true }
                        }
                    }) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Cerrar sesión", tint = BlancoPuro)
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
                    Surface(
                        color = verdeTurquesa,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Person, contentDescription = null, tint = BlancoPuro)
                            Spacer(Modifier.width(8.dp))
                            Text("Mi Perfil", color = BlancoPuro, fontWeight = FontWeight.Bold)
                        }
                    }

                    Column(modifier = Modifier.padding(16.dp)) {
                        InfoLabel(label = "NOMBRE", value = "Tania Sanchez Reyes")
                        Spacer(Modifier.height(12.dp))
                        Row(Modifier.fillMaxWidth()) {
                            Box(Modifier.weight(1f)) { InfoLabel(label = "MATRICULA", value = "20243ds026") }
                            Box(Modifier.weight(1f)) { InfoLabel(label = "CUATRIMESTRE", value = "7mo") }
                        }
                        Spacer(Modifier.height(12.dp))
                        InfoLabel(label = "CARRERA", value = "Ingeniería en Tecnologías de la Información")
                        Spacer(Modifier.height(12.dp))
                        Text("PUESTO", fontSize = 11.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                        Surface(
                            color = Color(0xFFE0F2F1),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.padding(top = 4.dp)
                        ) {
                            Text(
                                "Programador",
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
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
                    Surface(
                        color = azulMarino,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Assignment, contentDescription = null, tint = BlancoPuro)
                            Spacer(Modifier.width(8.dp))
                            Text("Proyecto asignado", color = BlancoPuro, fontWeight = FontWeight.Bold)
                        }
                    }

                    Column(modifier = Modifier.padding(16.dp)) {
                        InfoLabel(label = "NOMBRE PROYECTO", value = "Desarrollo de Plataforma Educativa")
                        Spacer(Modifier.height(12.dp))
                        Row(Modifier.fillMaxWidth()) {
                            Box(Modifier.weight(1f)) { InfoLabel(label = "FECHA INICIO", value = "01 / Ene / 2024") }
                            Box(Modifier.weight(1f)) { InfoLabel(label = "FECHA FIN", value = "31 / Dic / 2024") }
                        }
                        Spacer(Modifier.height(12.dp))
                        InfoLabel(label = "LIDER", value = "Juan Pérez")
                        Spacer(Modifier.height(12.dp))
                        InfoLabel(label = "OBJETIVO", value = "Digitalizar procesos académicos institucionales.")
                        Spacer(Modifier.height(12.dp))
                        InfoLabel(label = "DESCRIPCIÓN", value = "Implementación de un sistema integral para la gestión de proyectos y finanzas estudiantiles.")
                    }
                }
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