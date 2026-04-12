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
    onGuardar: (String, String) -> Unit
) {
    var contrasenaActual by remember { mutableStateOf("") }
    var nuevaContrasena by remember { mutableStateOf("") }
    var confirmarContrasena by remember { mutableStateOf("") }

    var visibleActual by remember { mutableStateOf(false) }
    var visibleNueva by remember { mutableStateOf(false) }
    var visibleConfirmar by remember { mutableStateOf(false) }

    var intentoGuardar by remember { mutableStateOf(false) }
    var errorValidacion by remember { mutableStateOf<String?>(null) }

    val azulEncabezado = AzulPrimario
    val verdeBoton = Color(0xFF00897B)
    val grisFondo = Color(0xFFF5F5F5)

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(12.dp),
            backgroundColor = Color.White,
            elevation = 16.dp,
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.85f)
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
                    IconButton(onClick = onDismiss, modifier = Modifier.size(24.dp)) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Cerrar",
                            tint = Color.White
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Por favor, ingrese su contraseña actual y su nueva contraseña.",
                        color = Color.Gray,
                        fontSize = 14.sp,
                        modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
                    )

                    CampoContrasena(
                        label = "CONTRASEÑA ACTUAL",
                        value = contrasenaActual,
                        onValueChange = { contrasenaActual = it },
                        isVisible = visibleActual,
                        onVisibilityChange = { visibleActual = it },
                        iconoStart = Icons.Outlined.Lock,
                        isError = intentoGuardar && contrasenaActual.isBlank()
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    CampoContrasena(
                        label = "NUEVA CONTRASEÑA",
                        value = nuevaContrasena,
                        onValueChange = { nuevaContrasena = it },
                        isVisible = visibleNueva,
                        onVisibilityChange = { visibleNueva = it },
                        iconoStart = Icons.Outlined.VpnKey,
                        isError = intentoGuardar && nuevaContrasena.isBlank()
                    )

                    Text(
                        text = "La contraseña debe tener al menos 8 caracteres, incluir mayúsculas, minúsculas, números y un símbolo.",
                        color = Color.Red,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp, bottom = 16.dp)
                    )

                    CampoContrasena(
                        label = "CONFIRMAR NUEVA CONTRASEÑA",
                        value = confirmarContrasena,
                        onValueChange = { confirmarContrasena = it },
                        isVisible = visibleConfirmar,
                        onVisibilityChange = { visibleConfirmar = it },
                        iconoStart = Icons.Outlined.Security,
                        isError = (intentoGuardar && confirmarContrasena.isBlank()) || (nuevaContrasena != confirmarContrasena && confirmarContrasena.isNotBlank())
                    )

                    if (nuevaContrasena != confirmarContrasena && confirmarContrasena.isNotBlank()) {
                        ErrorValidacion("Las contraseñas no coinciden")
                    }

                    if (errorValidacion != null) {
                        Spacer(modifier = Modifier.height(16.dp))
                        ErrorValidacion(errorValidacion!!)
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedButton(
                            onClick = onDismiss,
                            modifier = Modifier.weight(1f).height(48.dp),
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, verdeBoton)
                        ) {
                            Text("Cancelar", color = verdeBoton, fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = {
                                intentoGuardar = true
                                errorValidacion = null

                                if (contrasenaActual.isBlank() || nuevaContrasena.isBlank() || confirmarContrasena.isBlank()) {
                                    errorValidacion = "Todos los campos son obligatorios"
                                    return@Button
                                }

                                if (nuevaContrasena != confirmarContrasena) {
                                    errorValidacion = "Las contraseñas no coinciden"
                                    return@Button
                                }

                                onGuardar(contrasenaActual, nuevaContrasena)
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = verdeBoton),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f).height(48.dp),
                            elevation = ButtonDefaults.elevation(0.dp)
                        ) {
                            Text("Guardar", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CampoContrasena(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isVisible: Boolean,
    onVisibilityChange: (Boolean) -> Unit,
    iconoStart: ImageVector,
    isError: Boolean
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "$label *",
            fontSize = 13.sp,
            color = if (isError) Color.Red else Color(0xFF00334E),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 6.dp)
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

            leadingIcon = {
                Icon(
                    imageVector = iconoStart,
                    contentDescription = null,
                    tint = if (isError) Color.Red else Color.Gray
                )
            },

            trailingIcon = {
                IconButton(onClick = { onVisibilityChange(!isVisible) }) {
                    Icon(
                        imageVector = if (isVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = "Ver contraseña",
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
        fontWeight = FontWeight.Normal,
        modifier = Modifier.fillMaxWidth().padding(start = 4.dp, top = 2.dp)
    )
}