package com.sigpro.miembro.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.sigpro.lider.ui.theme.*
import com.sigpro.lider.viewmodel.HomeLiderViewModel

@Composable
fun NominasMiembroScreen(
    navController: NavController,
    //viewModel: NominasMimebrosViewModel = viewModel()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    var searchQuery by remember { mutableStateOf("") }
    val azulMarino = Color(0xFF00334E)
    val verdeTurquesa = Color(0xFF009688)

    val listaNominas = listOf(
        NominaItem("#130", "PAGO NÓMINA", "Viernes, 30 ene 2026", "Pago quincenal", "$4,800.00"),
        NominaItem("#129", "PAGO NÓMINA", "Viernes, 30 ene 2026", "Pago quincenal", "$4,800.00"),
        NominaItem("#128", "PAGO NÓMINA", "Viernes, 30 ene 2026", "Pago quincenal", "$4,800.00"),
        NominaItem("#127", "PAGO NÓMINA", "Viernes, 30 ene 2026", "Pago quincenal", "$4,800.00")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = AzulPrimario,
                title = {
                    Text(
                        "Nóminas",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                elevation = 0.dp
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
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                placeholder = { Text("Buscar", color = Color.Gray) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray) },
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = Color.White,
                    focusedBorderColor = Color.LightGray,
                    unfocusedBorderColor = Color.LightGray
                ),
                singleLine = true
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(listaNominas) { nomina ->
                    CardNomina(nomina)
                }
            }
        }
    }
}

@Composable
fun CardNomina(nomina: NominaItem) {
    Card(
        shape = RoundedCornerShape(4.dp),
        elevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = nomina.id,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = nomina.titulo,
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold,
                color = AzulPrimario,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text(
                text = nomina.fecha,
                fontSize = 13.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = nomina.descripcion,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                Text(
                    text = nomina.monto,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
            }
        }
    }
}

data class NominaItem(
    val id: String,
    val titulo: String,
    val fecha: String,
    val descripcion: String,
    val monto: String
)