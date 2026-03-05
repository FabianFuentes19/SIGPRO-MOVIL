package com.sigpro.lider.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sigpro.lider.R

/**
 * Actividad principal para el rol Líder (SIGPRO).
 * Punto de entrada de la aplicación.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
