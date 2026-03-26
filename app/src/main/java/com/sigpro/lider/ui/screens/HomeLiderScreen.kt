package com.sigpro.lider.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sigpro.lider.ui.components.ProyectoCard
import com.sigpro.lider.ui.components.MiembroItem
import com.sigpro.lider.ui.theme.SigproTheme

@Composable
fun HomeLiderScreen() {
    var showAgregarMiembroDialog by remember { mutableStateOf(false) }
    val azulMarino = Color(0xFF00334E)
    val doradoBoton = Color(0xFFA68238)

    Scaffold(
        backgroundColor = Color(0xFFF5F5F5),
        topBar = {
            TopAppBar(
                title = { Text("") },
                backgroundColor = azulMarino,
                elevation = 0.dp,
                modifier = Modifier.height(60.dp)
            )
        },
        bottomBar = {
            BottomNavigation(
                backgroundColor = azulMarino,
                contentColor = Color.White,
                elevation = 8.dp
            ) {
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Inicio", modifier = Modifier.size(30.dp)) },
                    selected = true,
                    onClick = { /* Navegación */ }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Payments, contentDescription = "Finanzas", modifier = Modifier.size(30.dp)) },
                    selected = false,
                    onClick = { /* Navegación */ }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Perfil", modifier = Modifier.size(30.dp)) },
                    selected = false,
                    onClick = { /* Navegación */ }
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))

                ProyectoCard(
                    nombre = "SIGPRO-MOVIL",
                    objetivo = "Sistema de gestión de recursos",
                    fechaInicio = "21/02/2026",
                    fechaFin = "29/04/2026",
                    presupuesto = "400,000",
                    progreso = 0.65f
                )

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Miembros",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = azulMarino
                    )

                    Button(
                        onClick = { showAgregarMiembroDialog = true },
                        colors = ButtonDefaults.buttonColors(backgroundColor = doradoBoton),
                        shape = RoundedCornerShape(24.dp),
                        elevation = ButtonDefaults.elevation(defaultElevation = 2.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Icon(
                            Icons.Default.PersonAdd,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(
                            text = "Agregar miembro",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            val listaFalsaDeMiembros = listOf("Marcos Ríos", "Ana García", "Luis Pineda")

            items(listaFalsaDeMiembros) { nombre ->
                MiembroItem(
                    nombre = nombre,
                    rol = "Diseñador",
                    onEditar = { },
                    onBorrar = { },
                    onDetalles = { },
                    onHistorial = { }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
    if (showAgregarMiembroDialog) {
        AgregarMiembroDialog(
            onDismiss = { showAgregarMiembroDialog = false },
            onGuardar = {
                println("Guardando miembro...")
                showAgregarMiembroDialog = false
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeLiderPreview() {
    SigproTheme {
        HomeLiderScreen()
    }
}