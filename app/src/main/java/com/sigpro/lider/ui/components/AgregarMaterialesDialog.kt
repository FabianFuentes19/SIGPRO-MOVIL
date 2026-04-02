package com.sigpro.lider.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.sigpro.lider.ui.theme.*

@Composable
fun AgregarMaterialDialog(
    onDismiss: () -> Unit,
    onRegistrar: (String, String, String) -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }

    var errorVisible by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = BlancoPuro,
            modifier = Modifier.fillMaxWidth().padding(10.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Agregar material",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = NegroTexto
                )

                Spacer(modifier = Modifier.height(24.dp))

                CustomLabelField(
                    label = "Nombre del Material *",
                    value = nombre,
                    placeholder = "Ej. Cemento Tolteca"
                ) {
                    nombre = it
                    errorVisible = false
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.weight(1f)) {
                        CustomLabelField(
                            label = "Cantidad *",
                            value = cantidad,
                            placeholder = "Ej. 10",
                            isNumber = true
                        ) {
                            cantidad = it
                            errorVisible = false
                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        CustomLabelField(
                            label = "Precio Unitario *",
                            value = precio,
                            placeholder = "Ej. 150.00",
                            isNumber = true
                        ) {
                            precio = it
                            errorVisible = false
                        }
                    }
                }

                if (errorVisible) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Todos los campos con * son obligatorios",
                        color = Color.Red,
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f).height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.5.dp, VerdeExito)
                    ) {
                        Text("Cancelar", color = VerdeExito, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Button(
                        onClick = {
                            if (nombre.isNotBlank() && cantidad.isNotBlank() && precio.isNotBlank()) {
                                onRegistrar(nombre, cantidad, precio)
                            } else {
                                errorVisible = true
                            }
                        },
                        modifier = Modifier.weight(1f).height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = VerdeExito)
                    ) {
                        Text("Registrar", color = BlancoPuro, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun CustomLabelField(
    label: String,
    value: String,
    placeholder: String,
    isNumber: Boolean = false,
    onValueChange: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 13.sp,
            color = GrisTextoSecundario,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, fontSize = 14.sp, color = Color.Gray.copy(0.5f)) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            singleLine = true,
            keyboardOptions = if (isNumber) KeyboardOptions(keyboardType = KeyboardType.Decimal) else KeyboardOptions.Default,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = AzulPrimario,
                unfocusedBorderColor = Color(0xFFD1D1D1),
                backgroundColor = Color(0xFFF9F9F9)
            )
        )
    }
}