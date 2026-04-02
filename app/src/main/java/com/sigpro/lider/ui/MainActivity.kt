package com.sigpro.lider.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sigpro.lider.ui.screens.*
import com.sigpro.lider.ui.theme.SigproTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SigproTheme {
                Surface(color = MaterialTheme.colors.background) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(
                onForgotPassword = { navController.navigate("forgot") },
                onLoginSucces = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        composable("forgot") {
            ForgotPasswordScreen(
                onBackToLogin = { navController.popBackStack() }, // Regresa a la anterior
                onGoToVerify = { navController.navigate("verify") }
            )
        }

        composable("verify") {
            VerifyCodeScreen(
                onBackToForgot = { navController.popBackStack() },
                onVerified = { navController.navigate("reset") }
            )
        }

        composable("reset") {
            ResetPasswordScreen(
                onBackToLogin = {
                    navController.navigate("login") {
                        popUpTo("forgot") { inclusive = true }
                    }
                }
            )
        }

        composable("home") {
            HomeLiderScreen(navController = navController)
        }

        composable("nominas") {
            HistorialNominasScreen(navController = navController)
        }

        composable("perfil") {
            PerfilLideresScreen (navController = navController)
        }
        composable("materiales") {
            MaterialesScreen(navController = navController)
        }
    }
}