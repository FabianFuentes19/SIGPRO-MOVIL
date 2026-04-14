package com.sigpro.miembro.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import com.sigpro.lider.models.VoucherDTO
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
                    onClick = {}
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
                placeholder = { Text("Buscar por quincena o estado", color = Color.Gray) },
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
            } else if (viewModel.listaVouchers.isEmpty()) {
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
                            text = "Por el momento no se han generado registros.\nEn cuanto se inicie tu primer periodo de pago, podrás consultarlo aquí.",
                            style = MaterialTheme.typography.body2,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 20.dp)
                ) {
                    val filteredList = viewModel.listaVouchers.filter {
                        it.numeroQuincena.toString().contains(searchQuery) || 
                        it.estado.lowercase().contains(searchQuery.lowercase())
                    }

                    items(filteredList) { voucher ->
                        CardVoucher(voucher)
                    }
                }
            }
        }
    }
}

@Composable
fun CardVoucher(voucher: VoucherDTO) {
    val azulMarino = Color(0xFF00334E)
    val verdeBoton = Color(0xFF00897B)
    val naranjaPendiente = Color(0xFFFFA000)
    val esPagado = voucher.estado == "PAGADO"

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
                    text = "QUINCENA #${voucher.numeroQuincena}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
                Surface(
                    color = if (esPagado) Color(0xFFE0F2F1) else Color(0xFFFFF3E0),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = voucher.estado,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = if (esPagado) Color(0xFF00796B) else Color(0xFFE65100)
                    )
                }
            }

            Text(
                text = "PAGO NÓMINA",
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                color = azulMarino,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.CalendarToday, null, modifier = Modifier.size(14.dp), tint = Color.Gray)
                Spacer(Modifier.width(6.dp))
                Text(
                    text = "${voucher.fechaInicio} al ${voucher.fechaFin}",
                    fontSize = 13.sp,
                    color = Color.DarkGray
                )
            }

            Divider(modifier = Modifier.padding(vertical = 16.dp), thickness = 0.8.dp, color = Color(0xFFEEEEEE))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = if (esPagado) "Monto Recibido" else "Monto Esperado",
                        fontSize = 13.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "$${String.format(Locale.US, "%,.2f", if (esPagado) (voucher.montoPagado ?: 0.0) else voucher.montoEsperado)}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = if (esPagado) Color(0xFF2E7D32) else azulMarino
                    )
                }
                
                if (esPagado) {
                    Column(horizontalAlignment = Alignment.End) {
                        Text("FECHA DE PAGO", fontSize = 10.sp, color = Color.Gray)
                        Text(voucher.fechaPagoReal ?: "-", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = verdeBoton)
                    }
                }
            }
        }
    }
}