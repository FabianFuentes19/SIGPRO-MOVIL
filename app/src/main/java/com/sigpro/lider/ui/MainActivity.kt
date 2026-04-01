package com.sigpro.lider.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.sigpro.lider.ui.screens.LoginScreen
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.sigpro.lider.R
import com.sigpro.lider.ui.screens.PerfilLideresScreen
import com.sigpro.lider.ui.theme.SigproTheme
import androidx.compose.material.Surface
import androidx.compose.material.MaterialTheme
import com.sigpro.lider.ui.screens.HomeLiderScreen
import com.sigpro.lider.ui.theme.SigproTheme // Importamos tu tema personalizado

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    LoginScreen()
                }
            }
        }
    }
}