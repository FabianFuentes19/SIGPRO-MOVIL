package com.sigpro.lider.ui.screens

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

@Composable
fun AgregarMiembroDialog(
    onDismiss: () -> Unit,
    onGuardar: () -> Unit
) {
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

    val azulMarino = Color(0xFF00334E)
    val verdeGuardar = Color(0xFF00897B)

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            modifier = Modifier.fillMaxWidth().padding(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
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

                // --- Campo Matrícula ---
                Text("Matrícula *", modifier = Modifier.fillMaxWidth(), fontSize = 12.sp, color = Color.Gray)
                OutlinedTextField(
                    value = matricula,
                    onValueChange = { matricula = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Ej. 20243ds001") },
                    shape = RoundedCornerShape(10.dp)
                )

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