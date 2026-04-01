package com.sigpro.lider.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.sigpro.lider.ui.screens.LoginScreen
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
            MaterialTheme {
                Surface {
                    LoginScreen()
                }
            }
        }
    }
}