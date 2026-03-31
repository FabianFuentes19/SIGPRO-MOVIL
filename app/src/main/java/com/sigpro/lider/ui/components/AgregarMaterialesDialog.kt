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
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Campo: Material
                CustomLabelField(label = "Material *", value = nombre, placeholder = "Ej. Acuarelas") { nombre = it }

                Spacer(modifier = Modifier.height(16.dp))

                // Fila: Cantidad y Precio
                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.weight(1f)) {
                        CustomLabelField(
                            label = "Cantidad *",
                            value = cantidad,
                            placeholder = "Ej. 3",
                            isNumber = true
                        ) { cantidad = it }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        CustomLabelField(
                            label = "Precio *",
                            value = precio,
                            placeholder = "Ej. $50.00",
                            isNumber = true
                        ) { precio = it }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Botones: Cancelar y Registrar
                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f).height(48.dp),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, VerdeExito)
                    ) {
                        Text("Cancelar", color = VerdeExito, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Button(
                        onClick = { onRegistrar(nombre, cantidad, precio) },
                        modifier = Modifier.weight(1f).height(48.dp),
                        shape = RoundedCornerShape(10.dp),
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
        Text(text = label, fontSize = 12.sp, color = GrisTextoSecundario, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, fontSize = 13.sp, color = Color.LightGray) },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            keyboardOptions = if (isNumber) KeyboardOptions(keyboardType = KeyboardType.Number) else KeyboardOptions.Default,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = AzulPrimario,
                unfocusedBorderColor = Color(0xFFE0E0E0)
            )
        )
    }
}