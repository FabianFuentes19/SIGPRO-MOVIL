package com.sigpro.lider.ui.screens
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@OptIn(ExperimentalMaterialApi::class)
=======
>>>>>>> 3.5.2_dashboard_materiales
@Composable
fun AgregarMiembroDialog(
    onDismiss: () -> Unit,
    onGuardar: () -> Unit
) {

    var nombre by remember { mutableStateOf("") }
    var matricula by remember { mutableStateOf("") }
    // Estados para cada campo (Igual que en tu imagen)
    var nombre by remember { mutableStateOf("") }
    var matricula by remember { mutableStateOf("") }
    var cuatrimestre by remember { mutableStateOf("") }
    var grupo by remember { mutableStateOf("") }
    var carrera by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var rol by remember { mutableStateOf("") }
    var puesto by remember { mutableStateOf("") }
    var salario by remember { mutableStateOf("") }
    var fechaIngreso by remember { mutableStateOf("01/03/2026") }

    // 2. Estados para los Menús Desplegables (Dropdowns)
    var cuatrimestre by remember { mutableStateOf("1") }
    var grupo by remember { mutableStateOf("A") }
    var carrera by remember { mutableStateOf("DS") }

    var expandedCuatri by remember { mutableStateOf(false) }
    var expandedGrupo by remember { mutableStateOf(false) }
    var expandedCarrera by remember { mutableStateOf(false) }

    val opcionesCuatri = listOf("1", "2", "3", "4", "5", "6")
    val opcionesGrupo = listOf("A", "B", "C", "D")
    val opcionesCarrera = listOf("DS", "DMI", "Meca", "Aero")

    val azulMarino = Color(0xFF00334E)
    val verdeBoton = Color(0xFF00897B)

    val azulMarino = Color(0xFF00334E)
    val verdeGuardar = Color(0xFF00897B)

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f),
            elevation = 8.dp

            modifier = Modifier.fillMaxWidth().padding(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Agregar miembro",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(16.dp))

                CustomLabel("Nombre completo")
                    .verticalScroll(rememberScrollState()), // Para que no se corte en pantallas chicas
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Agregar miembro", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))

                // --- Campo Nombre ---
                Text("Nombre completo *", modifier = Modifier.fillMaxWidth(), fontSize = 12.sp, color = Color.Gray)
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Ej. Juan Pérez") },
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))
                CustomLabel("Matrícula")
                // --- Campo Matrícula ---
                Text("Matrícula *", modifier = Modifier.fillMaxWidth(), fontSize = 12.sp, color = Color.Gray)
                OutlinedTextField(
                    value = matricula,
                    onValueChange = { matricula = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Ej. 20243ds001") },
                    shape = RoundedCornerShape(10.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.weight(1f)) {
                        CustomLabel("Cuatrimestre")
                        ExposedDropdownMenuBox(
                            expanded = expandedCuatri,
                            onExpandedChange = { expandedCuatri = !expandedCuatri }
                        ) {
                            OutlinedTextField(
                                value = cuatrimestre,
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCuatri) },
                                shape = RoundedCornerShape(10.dp)
                            )
                            ExposedDropdownMenu(
                                expanded = expandedCuatri,
                                onDismissRequest = { expandedCuatri = false }
                            ) {
                                opcionesCuatri.forEach { opcion ->
                                    DropdownMenuItem(onClick = {
                                        cuatrimestre = opcion
                                        expandedCuatri = false
                                    }) { Text(opcion) }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        CustomLabel("Grupo")
                        ExposedDropdownMenuBox(
                            expanded = expandedGrupo,
                            onExpandedChange = { expandedGrupo = !expandedGrupo }
                        ) {
                            OutlinedTextField(
                                value = grupo,
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedGrupo) },
                                shape = RoundedCornerShape(10.dp)
                            )
                            ExposedDropdownMenu(
                                expanded = expandedGrupo,
                                onDismissRequest = { expandedGrupo = false }
                            ) {
                                opcionesGrupo.forEach { opcion ->
                                    DropdownMenuItem(onClick = {
                                        grupo = opcion
                                        expandedGrupo = false
                                    }) { Text(opcion) }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                CustomLabel("Carrera")
                ExposedDropdownMenuBox(
                    expanded = expandedCarrera,
                    onExpandedChange = { expandedCarrera = !expandedCarrera },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = carrera,
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCarrera) },
                        shape = RoundedCornerShape(10.dp)
                    )
                    ExposedDropdownMenu(
                        expanded = expandedCarrera,
                        onDismissRequest = { expandedCarrera = false }
                    ) {
                        opcionesCarrera.forEach { opcion ->
                            DropdownMenuItem(onClick = {
                                carrera = opcion
                                expandedCarrera = false
                            }) { Text(opcion) }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                CustomLabel("Contraseña")
                Spacer(modifier = Modifier.height(8.dp))

                // --- Fila Cuatri y Grupo ---
                Row(Modifier.fillMaxWidth()) {
                    Column(Modifier.weight(1f)) {
                        Text("Cuatrimestre *", fontSize = 12.sp, color = Color.Gray)
                        OutlinedTextField(value = cuatrimestre, onValueChange = { cuatrimestre = it }, shape = RoundedCornerShape(10.dp))
                    }
                    Spacer(Modifier.width(8.dp))
                    Column(Modifier.weight(1f)) {
                        Text("Grupo *", fontSize = 12.sp, color = Color.Gray)
                        OutlinedTextField(value = grupo, onValueChange = { grupo = it }, shape = RoundedCornerShape(10.dp))
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // --- Campo Carrera ---
                Text("Carrera *", modifier = Modifier.fillMaxWidth(), fontSize = 12.sp, color = Color.Gray)
                OutlinedTextField(value = carrera, onValueChange = { carrera = it }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp))

                Spacer(modifier = Modifier.height(8.dp))

                // --- Campo Contraseña ---
                Text("Contraseña *", modifier = Modifier.fillMaxWidth(), fontSize = 12.sp, color = Color.Gray)
                OutlinedTextField(
                    value = contrasena,
                    onValueChange = { contrasena = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Ej. 123@$") },
                    shape = RoundedCornerShape(10.dp),
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.height(12.dp))

                CustomLabel("Rol")
                OutlinedTextField(
                    value = rol,
                    onValueChange = { rol = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Ej. Desarrollador") },
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                CustomLabel("Puesto")
                OutlinedTextField(
                    value = puesto,
                    onValueChange = { puesto = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Puesto:") },
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        CustomLabel("Salario quincenal")
                        OutlinedTextField(
                            value = salario,
                            onValueChange = { salario = it },
                            placeholder = { Text("Salario") },
                            shape = RoundedCornerShape(10.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        CustomLabel("Fecha de ingreso")
                        OutlinedTextField(
                            value = fechaIngreso,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { Icon(Icons.Default.CalendarToday, contentDescription = null, tint = Color.Gray) },
                            shape = RoundedCornerShape(10.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, verdeBoton)
                    ) {
                        Text("Cancelar", color = verdeBoton, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Button(
                        onClick = onGuardar,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = verdeBoton)
                    ) {
                        Text("Guardar", color = Color.White, fontWeight = FontWeight.Bold)
                    visualTransformation = PasswordVisualTransformation(),
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // --- Campo Rol ---
                Text("Rol *", modifier = Modifier.fillMaxWidth(), fontSize = 12.sp, color = Color.Gray)
                OutlinedTextField(value = rol, onValueChange = { rol = it }, modifier = Modifier.fillMaxWidth(), placeholder = { Text("Rol:") }, shape = RoundedCornerShape(10.dp))

                Spacer(modifier = Modifier.height(20.dp))

                // --- Botones finales ---
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF1B5E20))
                    ) {
                        Text("Cancelar")
                    }
                    Spacer(Modifier.width(12.dp))
                    Button(
                        onClick = onGuardar,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = verdeGuardar, contentColor = Color.White)
                    ) {
                        Text("Guardar")
                    }
                }
            }
        }
    }
}

@Composable
fun CustomLabel(texto: String) {
    Text(
        text = "$texto *",
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 4.dp, bottom = 2.dp),
        fontSize = 12.sp,
        color = Color.Gray
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AgregarMiembroPreview() {
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AgregarMiembroPreview() {
    // Usamos una caja (Box) para simular el fondo gris de la app
    // y ver cómo resalta el Dialog blanco encima.
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD1D5DB)),
        contentAlignment = Alignment.Center
    ) {
        AgregarMiembroDialog(
            onDismiss = {},
            onGuardar = {}
        )
    }
}