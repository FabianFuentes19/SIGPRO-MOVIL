package com.sigpro.lider.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.sigpro.lider.R
import com.sigpro.lider.ui.screens.MaterialesScreen
import com.sigpro.lider.ui.theme.SigproTheme


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SigproTheme {
                Surface(color = MaterialTheme.colors.background) {

                    MaterialesScreen(onBack = {
                        finish()
                    })

                }
            }
        }
    }
}
