package com.sigpro.lider.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sigpro.lider.ui.screens.*
import com.sigpro.lider.ui.theme.SigproTheme
import androidx.navigation.NavController
import com.sigpro.lider.session.SessionManager
import com.sigpro.miembro.ui.screens.NominasMiembroScreen

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
    val authViewModel: com.sigpro.lider.viewmodel.AuthViewModel = androidx.lifecycle.viewmodel.compose.viewModel()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(
                onForgotPassword = { navController.navigate("forgot") },
                onLoginSucces = {
                    val rol = SessionManager.getRol() ?: "MIEMBRO"
                    MapsByRole(navController, rol)
                }
            )
        }

        composable("forgot") {
            ForgotPasswordScreen(
                onBackToLogin = { navController.popBackStack() },
                onGoToVerify = { navController.navigate("verify") },
                viewModel = authViewModel
            )
        }

       composable("verify") {
            VerifyCodeScreen(
                onBackToForgot = { navController.popBackStack() },
                onVerified = { navController.navigate("reset") },
                viewModel = authViewModel
            )
        }

                composable("reset") {
                    ResetPasswordScreen(
                        onBackToLogin = {
                            navController.navigate("login") {
                                popUpTo("forgot") { inclusive = true }
                            }
                        },
                        viewModel = authViewModel
                    )
                }

        //Lider screens
        composable("home") {
            HomeLiderScreen(navController = navController)
        }

        composable("nominas") {
            HistorialNominasScreen(navController = navController)
        }

        composable("perfil") {
            PerfilLideresScreen(navController = navController)
        }

        //Mimebros screens
        composable("home_miembro") {
            HomeMiembroScreen(navController = navController)
        }

        composable("nominas_miembro") {
            NominasMiembroScreen(navController = navController)
        }

        composable(
            route = "materiales/{proyectoId}",
            arguments = listOf(navArgument("proyectoId") { type = NavType.LongType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("proyectoId") ?: 0L
            MaterialesScreen(navController, id)
        }
    }
}

fun MapsByRole(navController: NavController, rol: String?) {
    if (rol?.uppercase() == "LIDER") {
        navController.navigate("home") {
            popUpTo("login") { inclusive = true }
        }
    } else {
        navController.navigate("home_miembro") {
            popUpTo("login") { inclusive = true }
        }
    }
}