package com.sigpro.miembro.ui.screens

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
import com.sigpro.lider.models.PagoResponseDTO
import com.sigpro.lider.ui.theme.*
import com.sigpro.lider.viewmodel.NominasMiembroViewModel
import java.util.Locale

@Composable
fun NominasMiembroScreen(
    navController: NavController,
    viewModel: NominasMiembroViewModel = viewModel()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    var searchQuery by remember { mutableStateOf("") }
    val azulMarino = Color(0xFF00334E)

    LaunchedEffect(Unit) {
        viewModel.cargarNominas()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = AzulPrimario,
                title = { Text("Mis Nóminas", color = Color.White, fontWeight = FontWeight.Bold) },
                elevation = 4.dp
            )
        },
        bottomBar = {
            BottomNavigation(backgroundColor = azulMarino, contentColor = Color.White) {
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Payments, contentDescription = null) },
                    label = { Text("Nóminas") },
                    selected = currentRoute == "nominas_miembro",
                    onClick = { /* Estamos aquí */ }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = null) },
                    label = { Text("Perfil") },
                    selected = currentRoute == "home_miembro",
                    onClick = { navController.navigate("home_miembro") }
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
            // Buscador
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                placeholder = { Text("Buscar por fecha o folio", color = Color.Gray) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray) },
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = Color.White,
                    focusedBorderColor = AzulPrimario
                )
            )

            if (viewModel.cargando) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = AzulPrimario)
                }
            } else if (viewModel.listaPagos.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.Payments,
                            contentDescription = null,
                            modifier = Modifier.size(100.dp),
                            tint = Color.LightGray
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Tu historial de pagos aparecerá aquí",
                            style = MaterialTheme.typography.h6,
                            color = Color.DarkGray,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Por el momento no tienes nóminas registradas.\nEn cuanto se procese tu primer pago, podrás consultarlo aquí.",
                            style = MaterialTheme.typography.body2,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                        Button(
                            onClick = { viewModel.cargarNominas() },
                            modifier = Modifier.padding(top = 24.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = AzulPrimario),
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Text("Actualizar", color = Color.White)
                        }
                    }
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 20.dp)
                ) {
                    val filteredList = viewModel.listaPagos.filter {
                        it.id.toString().contains(searchQuery) || it.fecha.contains(searchQuery)
                    }

                    items(filteredList) { pago ->
                        CardNominaReal(pago)
                    }
                }
            }
        }
    }
}

@Composable
fun CardNominaReal(pago: PagoResponseDTO) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = 4.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "FOLIO #${pago.id}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
                Surface(
                    color = Color(0xFFE0F2F1),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "DEPÓSITO",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF00796B)
                    )
                }
            }

            Text(
                text = "PAGO NÓMINA",
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                color = AzulPrimario,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Fecha de emisión: ",
                    fontSize = 13.sp,
                    color = Color.Gray
                )
                Text(
                    text = pago.fecha,
                    fontSize = 13.sp,
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Medium
                )
            }

            Divider(modifier = Modifier.padding(vertical = 16.dp), thickness = 0.8.dp, color = Color(0xFFEEEEEE))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Monto Neto",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                Text(
                    text = "$${String.format(Locale.US, "%,.2f", pago.monto)}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF2E7D32)
                )
            }
        }
    }
}