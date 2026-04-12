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
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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
    var fechaIngreso by remember { mutableStateOf("") }

    var cuatrimestre by remember { mutableStateOf("") }
    var grupo by remember { mutableStateOf("") }
    var carrera by remember { mutableStateOf("") }

    var intentoGuardar by remember { mutableStateOf(false) }
    val nombreError = intentoGuardar && nombre.isBlank()
    val matriculaError = intentoGuardar && matricula.isBlank()
    val contrasenaError = intentoGuardar && contrasena.isBlank()
    val puestoError = intentoGuardar && puesto.isBlank()
    val salarioError = intentoGuardar && salario.isBlank()
    val fechaIngresoError = intentoGuardar && fechaIngreso.isBlank()
    val carreraError = intentoGuardar && carrera.isBlank()
    val grupoError = intentoGuardar && grupo.isBlank()
    val cuatriError = intentoGuardar && cuatrimestre.isBlank()




    var expandedCuatri by remember { mutableStateOf(false) }
    var expandedGrupo by remember { mutableStateOf(false) }
    var expandedCarrera by remember { mutableStateOf(false) }

    val opcionesCuatri = listOf("1", "2", "3", "4", "5", "6","7","8","9","10","11")
    val opcionesGrupo = listOf("A", "B", "C", "D","E","F","G")
    val opcionesCarrera = listOf("DS", "RD", "Meca", "Aero")
    var contrasenaVisible by remember { mutableStateOf(false) }

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
                        CustomLabel("Cuatrimestre", isError = cuatriError)
                        ExposedDropdownMenuBox(expanded = expandedCuatri, onExpandedChange = { expandedCuatri = !expandedCuatri }) {
                            OutlinedTextField(
                                value = cuatrimestre,
                                onValueChange = {},
                                readOnly = true,
                                isError = cuatriError,
                                placeholder = { Text("") },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCuatri) }, shape = RoundedCornerShape(10.dp))
                            ExposedDropdownMenu(expanded = expandedCuatri, onDismissRequest = { expandedCuatri = false }) {
                                opcionesCuatri.forEach { DropdownMenuItem(onClick = { cuatrimestre = it; expandedCuatri = false }) { Text(it) } }
                            }
                        }
                        if (cuatriError) ErrorText("Campo obligatorio")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        CustomLabel("Grupo",isError = grupoError)
                        ExposedDropdownMenuBox(expanded = expandedGrupo, onExpandedChange = { expandedGrupo = !expandedGrupo }) {
                            OutlinedTextField(
                                value = grupo,
                                onValueChange = {},
                                readOnly = true,
                                isError = grupoError,
                                placeholder = { Text("") },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedGrupo) }, shape = RoundedCornerShape(10.dp))
                            ExposedDropdownMenu(expanded = expandedGrupo, onDismissRequest = { expandedGrupo = false }) {
                                opcionesGrupo.forEach { DropdownMenuItem(onClick = { grupo = it; expandedGrupo = false }) { Text(it) } }
                            }
                        }
                        if (grupoError) ErrorText("Campo obligatorio")

                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                CustomLabel("Carrera",isError = carreraError)
                ExposedDropdownMenuBox(expanded = expandedCarrera, onExpandedChange = { expandedCarrera = !expandedCarrera }) {
                    OutlinedTextField(
                        value = carrera,
                        onValueChange = {},
                        readOnly = true,
                        isError = carreraError,
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCarrera) }, shape = RoundedCornerShape(10.dp))
                    ExposedDropdownMenu(expanded = expandedCarrera, onDismissRequest = { expandedCarrera = false }) {
                        opcionesCarrera.forEach { DropdownMenuItem(onClick = { carrera = it; expandedCarrera = false }) { Text(it) } }
                    }

                    if (carreraError) ErrorText("Campo obligatorio")

                }

                Spacer(modifier = Modifier.height(12.dp))

                CustomLabel("Contraseña", isError = contrasenaError)
                OutlinedTextField(
                    value = contrasena,
                    onValueChange = { contrasena = it },
                    isError = contrasenaError,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = if (contrasenaVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val imagen = if (contrasenaVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                        val descripcion = if (contrasenaVisible) "Ocultar contraseña" else "Mostrar contraseña"

                        IconButton(onClick = { contrasenaVisible = !contrasenaVisible }) {
                            Icon(imageVector = imagen, contentDescription = descripcion, tint = Color.Gray)
                        }
                    }
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
                }
                Spacer(modifier = Modifier.width(8.dp))

                Row (modifier = Modifier.fillMaxWidth()){
                    Column(modifier = Modifier.weight(1f)) {
                        CustomLabel("Fecha ingreso",isError = fechaIngresoError)
                        OutlinedTextField(
                            value = fechaIngreso,
                            onValueChange = {},
                            readOnly = true,
                            isError = fechaIngresoError,
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
                        if (fechaIngresoError) ErrorText("Campo obligatorio")
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
                                    salario.isNotBlank()&&
                                    fechaIngreso.isNotBlank() &&
                                    cuatrimestre.isNotBlank() &&
                                    grupo.isNotBlank() &&
                                    carrera.isNotBlank()

                            if (esFormularioValido) {
                                val partesFecha = fechaIngreso.split("/")
                                val fechaFormateada = if (partesFecha.size == 3) {
                                    "${partesFecha[2]}-${partesFecha[1].padStart(2, '0')}-${partesFecha[0].padStart(2, '0')}"
                                } else null

                                val nuevo = UsuarioDTO(
                                    nombreCompleto = nombre,
                                    matricula = matricula,
                                    contrasena = contrasena,
                                    grupo = grupo,
                                    carrera = carrera,
                                    cuatrimestre = cuatrimestre.toIntOrNull() ?: 1,
                                    puesto = puesto,
                                    salarioQuincenal = salario.toDoubleOrNull() ?: 0.0,
                                    fechaIngreso = fechaFormateada,
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