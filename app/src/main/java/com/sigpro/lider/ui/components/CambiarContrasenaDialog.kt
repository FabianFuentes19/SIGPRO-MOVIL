package com.sigpro.lider.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.VpnKey
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.sigpro.lider.ui.theme.*

@Composable
fun CambiarContrasenaDialog(
    onDismiss: () -> Unit,
    onGuardar: (String, String) -> Unit,
    cargando: Boolean = false,
    errorDesdeServidor: String? = null
) {
    var contrasenaActual by remember { mutableStateOf("") }
    var nuevaContrasena by remember { mutableStateOf("") }
    var confirmarContrasena by remember { mutableStateOf("") }

    var visibleActual by remember { mutableStateOf(false) }
    var visibleNueva by remember { mutableStateOf(false) }
    var visibleConfirmar by remember { mutableStateOf(false) }

    var intentoGuardar by remember { mutableStateOf(false) }
    var errorLocal by remember { mutableStateOf<String?>(null) }

    val passwordPattern = remember {
        Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$")
    }

    val azulEncabezado = AzulPrimario
    val verdeBoton = Color(0xFF00897B)

    Dialog(onDismissRequest = { if (!cargando) onDismiss() }) {
        Card(
            shape = RoundedCornerShape(12.dp),
            backgroundColor = Color.White,
            elevation = 16.dp,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(azulEncabezado)
                        .padding(horizontal = 20.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Cambiar Contraseña",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    IconButton(onClick = onDismiss, modifier = Modifier.size(24.dp), enabled = !cargando) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = null, tint = Color.White)
                    }
                }

                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Por favor, ingrese su contraseña actual y su nueva contraseña.",
                        color = Color.Gray,
                        fontSize = 14.sp,
                        modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp)
                    )

                    // CAMPO: CONTRASEÑA ACTUAL
                    CampoContrasena(
                        label = "CONTRASEÑA ACTUAL",
                        value = contrasenaActual,
                        onValueChange = { contrasenaActual = it },
                        isVisible = visibleActual,
                        onVisibilityChange = { visibleActual = it },
                        iconoStart = Icons.Outlined.Lock,
                        // El error se activa si el campo está vacío al intentar guardar O si el servidor mandó error
                        isError = (intentoGuardar && contrasenaActual.isBlank()) || errorDesdeServidor != null
                    )

                    // MENSAJE ESPECÍFICO DE ERROR
                    if (errorDesdeServidor != null) {
                        Text(
                            text = "Contraseña actual incorrecta",
                            color = Color.Red,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    val esValida = passwordPattern.matches(nuevaContrasena)

                    // CAMPO: NUEVA CONTRASEÑA
                    CampoContrasena(
                        label = "NUEVA CONTRASEÑA",
                        value = nuevaContrasena,
                        onValueChange = { nuevaContrasena = it },
                        isVisible = visibleNueva,
                        onVisibilityChange = { visibleNueva = it },
                        iconoStart = Icons.Outlined.VpnKey,
                        isError = intentoGuardar && (nuevaContrasena.isBlank() || !esValida)
                    )

                    Text(
                        text = "Mínimo 8 caracteres, Mayús., Minús., Núm. y Símbolo.",
                        color = if (intentoGuardar && !esValida) Color.Red else Color.Gray,
                        fontSize = 11.sp,
                        modifier = Modifier.fillMaxWidth().padding(top = 4.dp, bottom = 16.dp)
                    )

                    // CAMPO: CONFIRMAR
                    CampoContrasena(
                        label = "CONFIRMAR NUEVA CONTRASEÑA",
                        value = confirmarContrasena,
                        onValueChange = { confirmarContrasena = it },
                        isVisible = visibleConfirmar,
                        onVisibilityChange = { visibleConfirmar = it },
                        iconoStart = Icons.Outlined.Security,
                        isError = intentoGuardar && (confirmarContrasena.isBlank() || nuevaContrasena != confirmarContrasena)
                    )

                    if (nuevaContrasena != confirmarContrasena && confirmarContrasena.isNotBlank()) {
                        ErrorValidacion("Las contraseñas no coinciden")
                    }

                    if (errorLocal != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        ErrorValidacion(errorLocal!!)
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // BOTONES UNIFORMES
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedButton(
                            onClick = onDismiss,
                            enabled = !cargando,
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, Color.LightGray),
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                        ) {
                            Text(text = "Cerrar", color = Color.Gray, fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = {
                                intentoGuardar = true
                                errorLocal = null

                                if (contrasenaActual.isBlank() || nuevaContrasena.isBlank() || confirmarContrasena.isBlank()) {
                                    errorLocal = "Todos los campos son obligatorios"
                                    return@Button
                                }
                                if (!esValida) {
                                    errorLocal = "Contraseña no cumple requisitos"
                                    return@Button
                                }
                                if (nuevaContrasena != confirmarContrasena) {
                                    errorLocal = "Las contraseñas no coinciden"
                                    return@Button
                                }

                                // Ejecuta la función del ViewModel
                                onGuardar(contrasenaActual, nuevaContrasena)
                            },
                            enabled = !cargando,
                            colors = ButtonDefaults.buttonColors(backgroundColor = verdeBoton),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp),
                            elevation = ButtonDefaults.elevation(defaultElevation = 0.dp)
                        ) {
                            if (cargando) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    color = Color.White,
                                    strokeWidth = 2.dp
                                )
                            } else {
                                Text(text = "Guardar", color = Color.White, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CampoContrasena(
    label: String, value: String, onValueChange: (String) -> Unit,
    isVisible: Boolean, onVisibilityChange: (Boolean) -> Unit,
    iconoStart: ImageVector, isError: Boolean
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "$label *",
            fontSize = 12.sp,
            color = if (isError) Color.Red else Color(0xFF00334E),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            isError = isError,
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
            leadingIcon = { Icon(iconoStart, contentDescription = null, tint = if (isError) Color.Red else Color.Gray) },
            trailingIcon = {
                IconButton(onClick = { onVisibilityChange(!isVisible) }) {
                    Icon(
                        imageVector = if (isVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = null,
                        tint = Color.Gray
                    )
                }
            }
        )
    }
}

@Composable
fun ErrorValidacion(mensaje: String) {
    Text(
        text = mensaje,
        color = Color.Red,
        fontSize = 12.sp,
        modifier = Modifier.fillMaxWidth().padding(start = 4.dp, top = 2.dp)
    )
}