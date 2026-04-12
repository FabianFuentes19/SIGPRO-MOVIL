package com.sigpro.lider.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.sigpro.lider.models.UsuarioRequestDTO
import com.sigpro.lider.ui.screens.CustomLabel
import com.sigpro.lider.ui.screens.ErrorText
import java.math.BigDecimal

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EditarMiembroDialog(
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
    onGuardar: (UsuarioRequestDTO) -> Unit
) {
    var nombre by remember { mutableStateOf(nombrePre) }
    val matricula by remember { mutableStateOf(matriculaPre) }
    var contrasena by remember { mutableStateOf(contrasenaPre) }
    var rol by remember { mutableStateOf(rolPre) }
    var puesto by remember { mutableStateOf(puestoPre) }
    var salario by remember { mutableStateOf(salarioPre) }
    val fechaIngreso by remember { mutableStateOf(fechaPre) }

    var cuatrimestre by remember { mutableStateOf(cuatriPre) }
    var grupo by remember { mutableStateOf(grupoPre) }
    var carrera by remember { mutableStateOf(carreraPre) }

    var intentoGuardar by remember { mutableStateOf(false) }

    val nombreError = intentoGuardar && nombre.isBlank()
    val puestoError = intentoGuardar && puesto.isBlank()
    val salarioError = intentoGuardar && salario.isBlank()
    val cuatriError = intentoGuardar && cuatrimestre.isBlank()
    val grupoError = intentoGuardar && grupo.isBlank()
    val carreraError = intentoGuardar && carrera.isBlank()

    var expandedCuatri by remember { mutableStateOf(false) }
    var expandedGrupo by remember { mutableStateOf(false) }
    var expandedCarrera by remember { mutableStateOf(false) }

    val opcionesCuatri = listOf("1", "2", "3", "4", "5", "6","7","8","9","10","11")
    val opcionesGrupo = listOf("A", "B", "C", "D","E","F","G")
    val opcionesCarrera = listOf("DS", "RD", "Meca", "Aero")

    val verdeBoton = Color(0xFF00897B)

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f),
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

                CustomLabel("Nombre completo", isError = nombreError)
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    isError = nombreError,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                )
                if (nombreError) ErrorText("Campo obligatorio")

                Spacer(modifier = Modifier.height(8.dp))

                CustomLabel("Matrícula (No editable)")
                OutlinedTextField(
                    value = matricula,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(backgroundColor = Color(0xFFF5F5F5))
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.weight(1f)) {
                        CustomLabel("Cuatrimestre", isError = cuatriError)
                        ExposedDropdownMenuBox(expanded = expandedCuatri, onExpandedChange = { expandedCuatri = !expandedCuatri }) {
                            OutlinedTextField(value = cuatrimestre, onValueChange = {}, readOnly = true,isError = cuatriError, trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCuatri) }, shape = RoundedCornerShape(10.dp))
                            ExposedDropdownMenu(expanded = expandedCuatri, onDismissRequest = { expandedCuatri = false }) {
                                opcionesCuatri.forEach { op -> DropdownMenuItem(onClick = { cuatrimestre = op; expandedCuatri = false }) { Text(op) } }
                            }
                        }
                        if (cuatriError) ErrorText("Campo obligatorio")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        CustomLabel("Grupo", isError = grupoError)
                        ExposedDropdownMenuBox(expanded = expandedGrupo, onExpandedChange = { expandedGrupo = !expandedGrupo }) {
                            OutlinedTextField(value = grupo, onValueChange = {}, readOnly = true,isError = grupoError, trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedGrupo) }, shape = RoundedCornerShape(10.dp))
                            ExposedDropdownMenu(expanded = expandedGrupo, onDismissRequest = { expandedGrupo = false }) {
                                opcionesGrupo.forEach { op -> DropdownMenuItem(onClick = { grupo = op; expandedGrupo = false }) { Text(op) } }
                            }
                        }
                        if (grupoError) ErrorText("Campo obligatorio")
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                CustomLabel("Carrera", isError = carreraError)
                ExposedDropdownMenuBox(expanded = expandedCarrera, onExpandedChange = { expandedCarrera = !expandedCarrera }, modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(value = carrera, onValueChange = {}, readOnly = true,isError = carreraError, modifier = Modifier.fillMaxWidth(), trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCarrera) }, shape = RoundedCornerShape(10.dp))
                    ExposedDropdownMenu(expanded = expandedCarrera, onDismissRequest = { expandedCarrera = false }) {
                        opcionesCarrera.forEach { op -> DropdownMenuItem(onClick = { carrera = op; expandedCarrera = false }) { Text(op) } }
                    }
                }
                if (carreraError) ErrorText("Campo obligatorio")

                Spacer(modifier = Modifier.height(12.dp))

                CustomLabel("Puesto", isError = puestoError)
                OutlinedTextField(value = puesto, onValueChange = { puesto = it },isError = puestoError, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp))
                if (puestoError) ErrorText("Campo obligatorio")
                Spacer(modifier = Modifier.height(12.dp))

                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        CustomLabel("Salario quincenal", isError = salarioError)
                        OutlinedTextField(value = salario, onValueChange = { salario = it },isError = salarioError, shape = RoundedCornerShape(10.dp))
                        if (salarioError) ErrorText("Campo obligatorio")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                   /* Column(modifier = Modifier.weight(1f)) {
                        CustomLabel("Fecha de ingreso")
                        OutlinedTextField(
                            value = fechaIngreso, onValueChange = {}, readOnly = true,
                            trailingIcon = { Icon(Icons.Default.CalendarToday, contentDescription = null, tint = Color.Gray) },
                            shape = RoundedCornerShape(10.dp),
                            colors = TextFieldDefaults.outlinedTextFieldColors(backgroundColor = Color(0xFFF5F5F5))
                        )
                    }*/
                }

                Spacer(modifier = Modifier.height(32.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f).height(48.dp),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, verdeBoton)
                    ) {
                        Text("Cancelar", color = verdeBoton, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Button(
                        onClick = {
                            intentoGuardar = true

                            val esValido = nombre.isNotBlank() &&
                                    puesto.isNotBlank() &&
                                    salario.isNotBlank() &&
                                    cuatrimestre.isNotBlank() &&
                                    grupo.isNotBlank() &&
                                    carrera.isNotBlank()

                            if (esValido) {
                                val dtoParaBackend = UsuarioRequestDTO(
                                    matricula = matricula,
                                    nombreCompleto = nombre,
                                    contrasena = contrasena.ifBlank { "Password123!" },
                                    grupo = grupo,
                                    carrera = carrera,
                                    cuatrimestre = cuatrimestre.toIntOrNull() ?: 1,
                                    puesto = puesto,
                                    salarioQuincenal = salario.toBigDecimalOrNull() ?: BigDecimal.ONE,
                                    rolId = 2L
                                )
                                onGuardar(dtoParaBackend)
                            }
                        },
                        modifier = Modifier.weight(1f).height(48.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = verdeBoton)
                    ) {
                        Text("Guardar", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun CustomLabel(text: String, isError: Boolean = false) {
    Text(
        text = if (isError) "$text *" else text,
        fontSize = 13.sp,
        color = if (isError) Color.Red else Color.Gray,
        modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp),
        fontWeight = if (isError) FontWeight.Bold else FontWeight.Medium
    )
}

@Composable
fun ErrorText(mensaje: String) {
    Text(
        text = mensaje,
        color = Color.Red,
        fontSize = 11.sp,
        modifier = Modifier.fillMaxWidth().padding(start = 4.dp, top = 2.dp)
    )
}