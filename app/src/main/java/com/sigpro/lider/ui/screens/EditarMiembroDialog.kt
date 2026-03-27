package com.sigpro.lider.ui.screens

import android.view.Surface
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterialApi::class)
@Composable
fun EditarMiembroDialog(
    // Parámetros de precarga (los que usaremos para conectar al Back-end después)
    nombrePre: String,
    matriculaPre: String,
    cuatriPre: String,
    grupoPre: String,
    carreraPre: String,
    contrasenaPre: String,
    rolPre: String,
    puestoPre: String,
    salarioPre: String,
    fechaPre: String,
    onDismiss: () -> Unit,
    onGuardar: () -> Unit
) {
    // Estados inicializados con la información precargada
    var nombre by remember { mutableStateOf(nombrePre) }
    var matricula by remember { mutableStateOf(matriculaPre) }
    var contrasena by remember { mutableStateOf(contrasenaPre) }
    var rol by remember { mutableStateOf(rolPre) }
    var puesto by remember { mutableStateOf(puestoPre) }
    var salario by remember { mutableStateOf(salarioPre) }
    var fechaIngreso by remember { mutableStateOf(fechaPre) }

    // Estados para Dropdowns precargados
    var cuatrimestre by remember { mutableStateOf(cuatriPre) }
    var grupo by remember { mutableStateOf(grupoPre) }
    var carrera by remember { mutableStateOf(carreraPre) }

    var expandedCuatri by remember { mutableStateOf(false) }
    var expandedGrupo by remember { mutableStateOf(false) }
    var expandedCarrera by remember { mutableStateOf(false) }

    val opcionesCuatri = listOf("1", "2", "3", "4", "5", "6")
    val opcionesGrupo = listOf("A", "B", "C", "D")
    val opcionesCarrera = listOf("DS", "DMI", "Meca", "Aero")

    val verdeBoton = Color(0xFF00897B)

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
                .padding(vertical = 10.dp),
            elevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Editar miembro", fontSize = 20.sp, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(16.dp))

                // --- Nombre y Matrícula ---
                CustomLabel("Nombre completo")
                OutlinedTextField(value = nombre, onValueChange = { nombre = it }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp))

                Spacer(modifier = Modifier.height(8.dp))

                CustomLabel("Matrícula")
                OutlinedTextField(value = matricula, onValueChange = { matricula = it }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp))

                Spacer(modifier = Modifier.height(12.dp))

                // --- Dropdowns: Cuatri y Grupo ---
                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.weight(1f)) {
                        CustomLabel("Cuatrimestre")
                        ExposedDropdownMenuBox(expanded = expandedCuatri, onExpandedChange = { expandedCuatri = !expandedCuatri }) {
                            OutlinedTextField(value = cuatrimestre, onValueChange = {}, readOnly = true, trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCuatri) }, shape = RoundedCornerShape(10.dp))
                            ExposedDropdownMenu(expanded = expandedCuatri, onDismissRequest = { expandedCuatri = false }) {
                                opcionesCuatri.forEach { op -> DropdownMenuItem(onClick = { cuatrimestre = op; expandedCuatri = false }) { Text(op) } }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        CustomLabel("Grupo")
                        ExposedDropdownMenuBox(expanded = expandedGrupo, onExpandedChange = { expandedGrupo = !expandedGrupo }) {
                            OutlinedTextField(value = grupo, onValueChange = {}, readOnly = true, trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedGrupo) }, shape = RoundedCornerShape(10.dp))
                            ExposedDropdownMenu(expanded = expandedGrupo, onDismissRequest = { expandedGrupo = false }) {
                                opcionesGrupo.forEach { op -> DropdownMenuItem(onClick = { grupo = op; expandedGrupo = false }) { Text(op) } }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // --- Carrera ---
                CustomLabel("Carrera")
                ExposedDropdownMenuBox(expanded = expandedCarrera, onExpandedChange = { expandedCarrera = !expandedCarrera }, modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(value = carrera, onValueChange = {}, readOnly = true, modifier = Modifier.fillMaxWidth(), trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCarrera) }, shape = RoundedCornerShape(10.dp))
                    ExposedDropdownMenu(expanded = expandedCarrera, onDismissRequest = { expandedCarrera = false }) {
                        opcionesCarrera.forEach { op -> DropdownMenuItem(onClick = { carrera = op; expandedCarrera = false }) { Text(op) } }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // --- Contraseña y Rol ---
                CustomLabel("Contraseña")
                OutlinedTextField(value = contrasena, onValueChange = { contrasena = it }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp), visualTransformation = PasswordVisualTransformation())

                Spacer(modifier = Modifier.height(12.dp))

                CustomLabel("Rol")
                OutlinedTextField(value = rol, onValueChange = { rol = it }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp))

                Spacer(modifier = Modifier.height(12.dp))

                // --- Puesto ---
                CustomLabel("Puesto")
                OutlinedTextField(value = puesto, onValueChange = { puesto = it }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp))

                Spacer(modifier = Modifier.height(12.dp))

                // --- Salario y Fecha ---
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        CustomLabel("Salario quincenal")
                        OutlinedTextField(value = salario, onValueChange = { salario = it }, shape = RoundedCornerShape(10.dp))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        CustomLabel("Fecha de ingreso")
                        OutlinedTextField(
                            value = fechaIngreso, onValueChange = {}, readOnly = true,
                            trailingIcon = { Icon(Icons.Default.CalendarToday, contentDescription = null, tint = Color.Gray) },
                            shape = RoundedCornerShape(10.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(onClick = onDismiss, modifier = Modifier.weight(1f).height(48.dp), shape = RoundedCornerShape(10.dp), border = BorderStroke(1.dp, verdeBoton)) {
                        Text("Cancelar", color = verdeBoton, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Button(onClick = onGuardar, modifier = Modifier.weight(1f).height(48.dp), shape = RoundedCornerShape(10.dp), colors = ButtonDefaults.buttonColors(backgroundColor = verdeBoton)) {
                        Text("Guardar", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EditarMiembroMarcosPreview() {
    Box(
        modifier = Modifier.fillMaxSize().background(Color(0xFFD1D5DB)),
        contentAlignment = Alignment.Center
    ) {
        EditarMiembroDialog(
            nombrePre = "Marcos Ríos",
            matriculaPre = "20243DS010",
            cuatriPre = "5",
            grupoPre = "B",
            carreraPre = "DS",
            contrasenaPre = "Marcos123!",
            rolPre = "Administrador",
            puestoPre = "Líder de Proyecto",
            salarioPre = "18000.00",
            fechaPre = "15/01/2026",
            onDismiss = {},
            onGuardar = {}
        )
    }
}