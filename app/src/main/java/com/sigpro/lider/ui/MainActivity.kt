package com.sigpro.lider.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.sigpro.lider.R
import com.sigpro.lider.ui.screens.PerfilLideresScreen
import com.sigpro.lider.ui.theme.SigproTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SigproTheme {
                Surface(color = MaterialTheme.colors.background) {
                    PerfilLideresScreen(onNavigateToLogin = {
                        println("Cerrando sesión...")
                    })
                }
            }
        }
    }
}