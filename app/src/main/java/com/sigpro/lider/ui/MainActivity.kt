package com.sigpro.lider.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.sigpro.lider.api.ApiClient
import com.sigpro.lider.databinding.ActivityMainBinding
import com.sigpro.lider.models.LoginRequest
import com.sigpro.lider.models.LoginResponse
import com.sigpro.lider.session.SessionManager
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response

/**
 * Actividad principal para el login de SIGPRO.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val matricula = binding.etMatricula.text.toString().trim()
            val pass = binding.etContrasena.text.toString().trim()

            if (matricula.isNotEmpty() && pass.isNotEmpty()) {
                ejecutarLogin(matricula, pass)
            } else {
                Toast.makeText(this, "Por favor llena todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun ejecutarLogin(u: String, p: String) {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnLogin.isEnabled = false

        lifecycleScope.launch {
            try {
                val request = LoginRequest(u, p)
                val response = ApiClient.apiService.login(request)

                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && !body.token.isNullOrBlank()) {
                        // Guarda la sesión (token y rol)
                        SessionManager.saveSession(body.token, body.rol)

                        Toast.makeText(
                            this@MainActivity,
                            "¡Bienvenido a SIGPRO!",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "Respuesta inválida del servidor.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    val errorMessage = parseErrorResponse(response)
                    Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@MainActivity,
                    "Error de conexión: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            } finally {
                binding.progressBar.visibility = View.GONE
                binding.btnLogin.isEnabled = true
            }
        }
    }

    /**
     * Extrae el mensaje de error específico enviado por el backend cuando responde 401
     * con un JSON del tipo { "error": "mensaje" }.
     */
    private fun parseErrorResponse(response: Response<LoginResponse>): String {
        return try {
            val errorBody = response.errorBody()?.string()
            if (!errorBody.isNullOrBlank()) {
                val json = JSONObject(errorBody)
                val backendMessage = json.optString("error")
                if (backendMessage.isNotBlank()) {
                    backendMessage
                } else {
                    "Error al iniciar sesión."
                }
            } else {
                "Error al iniciar sesión."
            }
        } catch (e: Exception) {
            "Error al iniciar sesión."
        }
    }
}