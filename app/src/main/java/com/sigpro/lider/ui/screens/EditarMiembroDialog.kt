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
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun EditarMiembroDialog(
    nombrePre: String,
    matriculaPre: String,
    puestoPre: String,
    salarioPre: String,
    rolPre: String,
    onDismiss: () -> Unit,
    onGuardar: () -> Unit
) {
    var nombre by remember { mutableStateOf(nombrePre) }
    var matricula by remember { mutableStateOf(matriculaPre) }
    var puesto by remember { mutableStateOf(puestoPre) }
    var salario by remember { mutableStateOf(salarioPre) }
    var rol by remember { mutableStateOf(rolPre) }
    var contrasena by remember { mutableStateOf("123@") }

    var cuatrimestre by remember { mutableStateOf("5") }
    var grupo by remember { mutableStateOf("B") }
    var carrera by remember { mutableStateOf("DS") }

    val verdeBoton = Color(0xFF00897B)

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.9f).padding(vertical = 10.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp).verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Editar miembro", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))

                CustomLabel("Nombre completo")
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                CustomLabel("Matrícula")
                OutlinedTextField(
                    value = matricula,
                    onValueChange = { matricula = it },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))
                CustomLabel("Rol")
                OutlinedTextField(
                    value = rol,
                    onValueChange = { rol = it },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))
                CustomLabel("Puesto")
                OutlinedTextField(
                    value = puesto,
                    onValueChange = { puesto = it },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f).height(48.dp),
                        border = BorderStroke(1.dp, verdeBoton)
                    ) {
                        Text("Cancelar", color = verdeBoton)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Button(
                        onClick = onGuardar,
                        modifier = Modifier.weight(1f).height(48.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = verdeBoton)
                    ) {
                        Text("Guardar", color = Color.White)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EditarMiembroPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD1D5DB)),
        contentAlignment = Alignment.Center
    ) {
        EditarMiembroDialog(
            nombrePre = "Marcos Ríos",
            matriculaPre = "20243DS010",
            puestoPre = "Líder de Proyecto",
            salarioPre = "18,000.00",
            rolPre = "Administrador",
            onDismiss = {},
            onGuardar = {}
        )
    }
}