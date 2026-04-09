package com.sigpro.lider.ui.screens

import android.app.DatePickerDialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.sigpro.lider.models.UsuarioDTO
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AgregarMiembroDialog(
    onDismiss: () -> Unit,
    onGuardar: (UsuarioDTO) -> Unit
) {
    val context = LocalContext.current
    val verdeBoton = Color(0xFF00897B)

    var nombre by remember { mutableStateOf("") }
    var matricula by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var puesto by remember { mutableStateOf("") }
    var salario by remember { mutableStateOf("") }
    var fechaIngreso by remember { mutableStateOf("04/04/2026") }

    var cuatrimestre by remember { mutableStateOf("5") }
    var grupo by remember { mutableStateOf("A") }
    var carrera by remember { mutableStateOf("DS") }

    var intentoGuardar by remember { mutableStateOf(false) }
    val nombreError = intentoGuardar && nombre.isBlank()
    val matriculaError = intentoGuardar && matricula.isBlank()
    val contrasenaError = intentoGuardar && contrasena.isBlank()
    val puestoError = intentoGuardar && puesto.isBlank()
    val salarioError = intentoGuardar && salario.isBlank()

    var expandedCuatri by remember { mutableStateOf(false) }
    var expandedGrupo by remember { mutableStateOf(false) }
    var expandedCarrera by remember { mutableStateOf(false) }

    val opcionesCuatri = listOf("1", "2", "3", "4", "5", "6")
    val opcionesGrupo = listOf("A", "B", "C", "D")
    val opcionesCarrera = listOf("DS", "DMI", "Meca", "Aero")

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.9f),
            elevation = 8.dp
        ) {
            Column(
                modifier = Modifier.padding(20.dp).verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Agregar miembro", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))

                CustomLabel("Nombre completo", isError = nombreError)
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    isError = nombreError,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Ej. Pérez, Juan") },
                    shape = RoundedCornerShape(10.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, capitalization = KeyboardCapitalization.Words)
                )
                if (nombreError) ErrorText("Campo obligatorio")

                Spacer(modifier = Modifier.height(8.dp))

                CustomLabel("Matrícula", isError = matriculaError)
                OutlinedTextField(
                    value = matricula,
                    onValueChange = { matricula = it },
                    isError = matriculaError,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Ej. 20243ds001") },
                    shape = RoundedCornerShape(10.dp)
                )
                if (matriculaError) ErrorText("Campo obligatorio")

                Spacer(modifier = Modifier.height(12.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.weight(1f)) {
                        CustomLabel("Cuatrimestre")
                        ExposedDropdownMenuBox(expanded = expandedCuatri, onExpandedChange = { expandedCuatri = !expandedCuatri }) {
                            OutlinedTextField(value = cuatrimestre, onValueChange = {}, readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCuatri) }, shape = RoundedCornerShape(10.dp))
                            ExposedDropdownMenu(expanded = expandedCuatri, onDismissRequest = { expandedCuatri = false }) {
                                opcionesCuatri.forEach { DropdownMenuItem(onClick = { cuatrimestre = it; expandedCuatri = false }) { Text(it) } }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        CustomLabel("Grupo")
                        ExposedDropdownMenuBox(expanded = expandedGrupo, onExpandedChange = { expandedGrupo = !expandedGrupo }) {
                            OutlinedTextField(value = grupo, onValueChange = {}, readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedGrupo) }, shape = RoundedCornerShape(10.dp))
                            ExposedDropdownMenu(expanded = expandedGrupo, onDismissRequest = { expandedGrupo = false }) {
                                opcionesGrupo.forEach { DropdownMenuItem(onClick = { grupo = it; expandedGrupo = false }) { Text(it) } }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                CustomLabel("Carrera")
                ExposedDropdownMenuBox(expanded = expandedCarrera, onExpandedChange = { expandedCarrera = !expandedCarrera }) {
                    OutlinedTextField(value = carrera, onValueChange = {}, readOnly = true, modifier = Modifier.fillMaxWidth(),
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCarrera) }, shape = RoundedCornerShape(10.dp))
                    ExposedDropdownMenu(expanded = expandedCarrera, onDismissRequest = { expandedCarrera = false }) {
                        opcionesCarrera.forEach { DropdownMenuItem(onClick = { carrera = it; expandedCarrera = false }) { Text(it) } }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                CustomLabel("Contraseña", isError = contrasenaError)
                OutlinedTextField(
                    value = contrasena,
                    onValueChange = { contrasena = it },
                    isError = contrasenaError,
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    shape = RoundedCornerShape(10.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
                if (contrasenaError) ErrorText("Campo obligatorio")

                Spacer(modifier = Modifier.height(12.dp))

                CustomLabel("Puesto", isError = puestoError)
                OutlinedTextField(
                    value = puesto,
                    onValueChange = { puesto = it },
                    isError = puestoError,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                )
                if (puestoError) ErrorText("Campo obligatorio")

                Spacer(modifier = Modifier.height(12.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.weight(1f)) {
                        CustomLabel("Salario quincenal", isError = salarioError)
                        OutlinedTextField(
                            value = salario,
                            onValueChange = { if (it.all { c -> c.isDigit() || c == '.' }) salario = it },
                            isError = salarioError,
                            placeholder = { Text("0.00") },
                            shape = RoundedCornerShape(10.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                        )
                        if (salarioError) ErrorText("Campo obligatorio")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        CustomLabel("Fecha ingreso")
                        OutlinedTextField(
                            value = fechaIngreso,
                            onValueChange = {},
                            readOnly = true,
                            shape = RoundedCornerShape(10.dp),
                            trailingIcon = {
                                IconButton(onClick = {
                                    val cal = Calendar.getInstance()
                                    DatePickerDialog(context, { _, y, m, d ->
                                        fechaIngreso = String.format("%02d/%02d/%d", d, m + 1, y)
                                    }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
                                }) {
                                    Icon(Icons.Default.CalendarToday, null, tint = Color.Gray)
                                }
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(onClick = onDismiss, modifier = Modifier.weight(1f).height(48.dp),
                        shape = RoundedCornerShape(10.dp), border = BorderStroke(1.dp, verdeBoton)) {
                        Text("Cancelar", color = verdeBoton)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Button(
                        onClick = {
                            intentoGuardar = true
                            val esFormularioValido = nombre.isNotBlank() &&
                                    matricula.isNotBlank() &&
                                    contrasena.isNotBlank() &&
                                    puesto.isNotBlank() &&
                                    salario.isNotBlank()

                            if (esFormularioValido) {
                                val nuevo = UsuarioDTO(
                                    nombreCompleto = nombre,
                                    matricula = matricula,
                                    contrasena = contrasena,
                                    grupo = grupo,
                                    carrera = carrera,
                                    cuatrimestre = cuatrimestre.toIntOrNull() ?: 1,
                                    puesto = puesto,
                                    salarioQuincenal = salario.toDoubleOrNull() ?: 0.0,
                                    estado = "ACTIVO"
                                )
                                onGuardar(nuevo)
                            }
                        },
                        modifier = Modifier.weight(1f).height(48.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = verdeBoton)
                    ) {
                        Text("Guardar", color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun CustomLabel(texto: String, isError: Boolean = false) {
    Text(
        text = "$texto *",
        modifier = Modifier.fillMaxWidth().padding(start = 4.dp, bottom = 2.dp),
        fontSize = 12.sp,
        color = if (isError) Color.Red else Color.Gray,
        fontWeight = if (isError) FontWeight.Bold else FontWeight.Normal
    )
}

@Composable
fun ErrorText(mensaje: String) {
    Text(
        text = mensaje,
        color = Color.Red,
        fontSize = 10.sp,
        modifier = Modifier.fillMaxWidth().padding(start = 8.dp)
    )
}