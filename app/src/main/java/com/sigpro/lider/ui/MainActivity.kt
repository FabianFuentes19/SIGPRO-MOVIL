package com.sigpro.lider.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.compose.material.MaterialTheme
import com.sigpro.lider.ui.screens.HomeLiderScreen
import com.sigpro.lider.ui.theme.SigproTheme // Importamos tu tema personalizado

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SigproTheme {
                Surface(color = MaterialTheme.colors.background) {
                    HomeLiderScreen()
                }
            }
        }
    }
}