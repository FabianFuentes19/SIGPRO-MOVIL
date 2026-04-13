package com.sigpro.lider.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.sigpro.lider.models.UsuarioDTO
import com.sigpro.lider.ui.components.*
import com.sigpro.lider.ui.theme.AzulPrimario
import com.sigpro.lider.ui.theme.BlancoPuro
import com.sigpro.lider.viewmodel.HomeLiderViewModel

@Composable
fun HomeLiderScreen(
    navController: NavController,
    viewModel: HomeLiderViewModel = viewModel()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val context = androidx.compose.ui.platform.LocalContext.current

    // Estados de diálogos
    var showEditarDialog by remember { mutableStateOf(false) }
    var showAgregarMiembroDialog by remember { mutableStateOf(false) }
    var showEliminarMiembroDialog by remember { mutableStateOf(false) }
    var showConsultarMiembroDialog by remember { mutableStateOf(false) }
    var showHistorialMiembroDialog by remember { mutableStateOf(false) }
    var miembroSeleccionado by remember { mutableStateOf<UsuarioDTO?>(null) }

    LaunchedEffect(Unit) {
        viewModel.cargarProyecto()
    }

    val proyecto = viewModel.proyecto
    val azulMarino = Color(0xFF00334E)
    val doradoBoton = Color(0xFFA68238)

    Scaffold(
        backgroundColor = Color(0xFFF5F5F5),
        topBar = {
            TopAppBar(
                title = { Text("Inicio", color = BlancoPuro, fontWeight = FontWeight.Bold) },
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
                        if (currentRoute != "home") {
                            navController.navigate("home") {
                                popUpTo("home") { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
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
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))

                if (viewModel.cargando) {
                    Box(Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = AzulPrimario)
                    }
                } else if (proyecto != null && proyecto.id != null) {
                    ProyectoCard(
                        proyecto = proyecto,
                        onClick = {
                            navController.navigate("materiales/${proyecto.id}")
                        }
                    )
                } else {
                    // Vista líder sin proyecto
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        shape = RoundedCornerShape(12.dp),
                        elevation = 2.dp,
                        backgroundColor = Color.White
                    ) {
                        Row(
                            modifier = Modifier.padding(24.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Inventory2,
                                contentDescription = null,
                                modifier = Modifier.size(48.dp),
                                tint = Color(0xFF94A3B8)
                            )

                            Spacer(modifier = Modifier.width(20.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Sin proyecto asignado",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF1E293B) // Azul muy oscuro/Slate
                                )
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    text = "Actualmente no cuentas con un proyecto vinculado. Una vez que se te asigne uno, podrás gestionar a tus miembros y registrar materiales/nóminas.",
                                    fontSize = 14.sp,
                                    color = Color(0xFF64748B), // Gris Slate
                                    lineHeight = 20.sp
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                if (proyecto != null) {
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
                            // deshabilitado si no hay proyecto
                            enabled = (proyecto != null && proyecto.id != null),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = doradoBoton,
                                disabledBackgroundColor = Color(0xFFE2E8F0)
                            ),
                            shape = RoundedCornerShape(24.dp),
                            elevation = ButtonDefaults.elevation(
                                defaultElevation = 2.dp,
                                disabledElevation = 0.dp
                            ),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            val contenidoColor = if (proyecto != null) Color.White else Color(0xFF94A3B8)

                            Icon(
                                Icons.Default.PersonAdd,
                                contentDescription = null,
                                tint = contenidoColor,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(Modifier.width(6.dp))
                            Text(
                                text = "Agregar miembro",
                                color = contenidoColor,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            //lista de miembros si hay proyecto asignado
            if (proyecto != null) {
                if (viewModel.listaMiembros.isEmpty() && !viewModel.cargando) {
                    item {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 40.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Aún no hay participantes en el proyecto", fontSize = 16.sp, color = Color.Gray)
                        }
                    }
                } else {
                    items(viewModel.listaMiembros) { miembro ->
                        MiembroItem(
                            nombre = miembro.nombreCompleto ?: "Sin nombre",
                            rol = miembro.puesto ?: "Miembro",
                            onEditar = {
                                miembroSeleccionado = miembro
                                showEditarDialog = true
                            },
                            onBorrar = {
                                miembroSeleccionado = miembro
                                showEliminarMiembroDialog = true
                            },
                            onDetalles = {
                                if (miembro.matricula.isNotBlank()) {
                                    viewModel.cargarDetalleMiembro(miembro.matricula) {
                                        showConsultarMiembroDialog = true
                                    }
                                }
                            },
                            onHistorial = {
                                miembroSeleccionado = miembro
                                showHistorialMiembroDialog = true
                            }
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }

    if (showAgregarMiembroDialog && proyecto != null) {
        AgregarMiembroDialog(
            onDismiss = { showAgregarMiembroDialog = false },
            onGuardar = { miembro ->
                viewModel.agregarMiembro(context, miembro)
                showAgregarMiembroDialog = false
            }
        )
    }

    if (showEditarDialog && miembroSeleccionado != null) {
        val m = miembroSeleccionado!!
        EditarMiembroDialog(
            nombrePre = m.nombreCompleto ?: "",
            matriculaPre = m.matricula ?: "",
            cuatriPre = m.cuatrimestre?.toString() ?: "0",
            grupoPre = m.grupo ?: "",
            carreraPre = m.carrera ?: "",
            contrasenaPre = "",
            rolPre = m.rolNombre ?: "Miembro",
            puestoPre = m.puesto ?: "",
            salarioPre = m.salarioQuincenal?.toString() ?: "0.0",
            fechaPre = m.fechaIngreso?.toString() ?: "",
            onDismiss = { showEditarDialog = false },
            onGuardar = { usuarioEditado ->
                viewModel.editarMiembro(context, miembroSeleccionado!!.matricula, usuarioEditado)
                showEditarDialog = false
            }
        )
    }

    if (showEliminarMiembroDialog && miembroSeleccionado != null) {
        EliminarMiembroDialog(
            nombre = miembroSeleccionado!!.nombreCompleto,
            matricula = miembroSeleccionado!!.matricula,
            onDismiss = {
                showEliminarMiembroDialog = false
                miembroSeleccionado = null
            },
            onConfirmarEliminar = {
                viewModel.eliminarMiembro(context, miembroSeleccionado!!.matricula)
                showEliminarMiembroDialog = false
                miembroSeleccionado = null
            }
        )
    }

    if (showConsultarMiembroDialog && viewModel.miembroDetallado != null) {
        val m = viewModel.miembroDetallado!!
        ConsultarMiembroDialog(
            nombre = m.nombreCompleto,
            matricula = m.matricula,
            cuatrimestre = m.cuatrimestre.toString(),
            grupo = m.grupo,
            carrera = m.carrera,
            puesto = m.puesto,
            salario = m.salarioQuincenal.toString(),
            onDismiss = { showConsultarMiembroDialog = false }
        )
    }

    if (showHistorialMiembroDialog && miembroSeleccionado != null) {
        LaunchedEffect(miembroSeleccionado) {
            viewModel.cargarPagosMiembro(miembroSeleccionado!!.matricula)
        }

        HistorialMiembroDialog(
            nombre = miembroSeleccionado!!.nombreCompleto,
            puesto = miembroSeleccionado!!.puesto,
            totalAcumulado = String.format("$%,.2f", viewModel.listaPagosMiembro.sumOf { it.monto?.toDouble() ?: 0.0 }),

            listaPagos = viewModel.listaPagosMiembro.map {
                Pago(
                    fecha = it.fecha?.toString() ?: "Sin fecha",
                    monto = "$${String.format("%.2f", it.monto ?: 0.0)}"
                )
            },
            cargando = viewModel.cargando,
            onDismiss = {
                showHistorialMiembroDialog = false
                viewModel.listaPagosMiembro.clear()
            }
        )
    }
}