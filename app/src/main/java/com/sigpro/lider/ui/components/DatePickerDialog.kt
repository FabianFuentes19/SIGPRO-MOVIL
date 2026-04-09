package com.sigpro.lider.ui.components

import android.app.DatePickerDialog
import android.content.Context
import java.util.Calendar

fun mostrarCalendario(context: Context, fechaActual: String, onFechaSeleccionada: (String) -> Unit) {
    val calendario = Calendar.getInstance()

    val picker = DatePickerDialog(
        context,
        { _, año, mes, dia ->
            val fechaFormateada = String.format("%02d/%02d/%d", dia, mes + 1, año)
            onFechaSeleccionada(fechaFormateada)
        },
        calendario.get(Calendar.YEAR),
        calendario.get(Calendar.MONTH),
        calendario.get(Calendar.DAY_OF_MONTH)
    )
    picker.show()
}