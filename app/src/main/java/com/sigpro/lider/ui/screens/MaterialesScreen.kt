package com.sigpro.lider.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sigpro.lider.ui.components.AgregarMaterialDialog
import com.sigpro.lider.ui.theme.*

@Composable
fun MaterialesScreen(navController: NavController) {
    var showAgregarDialog by remember { mutableStateOf(false) }
    var searchTexto by remember { mutableStateOf("") }

    val fondoGris = GrisFondoApp
    val azulPrimario = AzulPrimario
    val verdeExito = VerdeExito

    Scaffold(
        backgroundColor = fondoGris,
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) { // <--- Agregué las llaves { }
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar", tint = BlancoPuro)
                    }
                },
                backgroundColor = azulPrimario,
                elevation = 0.dp
            )
        },

        ) { padding ->
        if (showAgregarDialog) {
            AgregarMaterialDialog(
                onDismiss = { showAgregarDialog = false },
                onRegistrar = { nombre, cantidad, precio ->
                    println("Registrando: $nombre, Cant: $cantidad, Precio: $precio")
                    showAgregarDialog = false
                }
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Materiales",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = NegroTexto
                )

                FloatingActionButton(
                    onClick = { showAgregarDialog = true },
                    backgroundColor = verdeExito,
                    contentColor = BlancoPuro,
                    elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 4.dp),
                    modifier = Modifier.size(56.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar", modifier = Modifier.size(30.dp))
                }
            }
            Spacer(modifier = Modifier.height(20.dp))

            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = 0.dp,
                backgroundColor = BlancoPuro,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        text = "TOTAL DE MATERIAL:",
                        fontSize = 12.sp,
                        color = GrisTextoSecundario,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "$12,500.00",
                        fontSize = 32.sp,
                        color = NegroTexto,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = searchTexto,
                onValueChange = { searchTexto = it },
                placeholder = { Text("Buscar materiales...", color = GrisTextoSecundario) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = GrisTextoSecundario) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = BlancoPuro,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = azulPrimario
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            val listaMateriales = listOf(
                MaterialData("Acuarelas", "4 pz", "$320.00"),
                MaterialData("Pinceles Óleo", "12 pz", "$546.00"),
                MaterialData("Lienzo 40x60", "2 pz", "$240.00"),
                MaterialData("Barniz Mate", "5 pz", "$1,050.00"),
                MaterialData("Espátulas Metálicas", "3 pz", "$225.00"),
                MaterialData("Gesso Blanco", "1 pz", "$180.00")
            )

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(listaMateriales) { material ->
                    CardMaterialItem(material)
                }
                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }
    }
}

@Composable
fun CardMaterialItem(material: MaterialData) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = 1.dp,
        backgroundColor = BlancoPuro,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = material.nombre, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = NegroTexto)
                Text(text = material.cantidad, fontSize = 12.sp, color = GrisTextoSecundario)
            }
            Text(text = material.precio, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = NegroTexto)
        }
    }
}

data class MaterialData(val nombre: String, val cantidad: String, val precio: String)

/*@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Vista Previa Materiales"
)
@Composable
fun MaterialesPreview() {
    SigproTheme {
        MaterialesScreen {
        }
    }
}*/