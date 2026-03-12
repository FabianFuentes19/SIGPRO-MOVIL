package com.sigpro.lider.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.sigpro.lider.api.ApiClient
import com.sigpro.lider.databinding.ActivityMainBinding
import com.sigpro.lider.models.LoginRequest
import kotlinx.coroutines.launch

/**
 * Actividad principal para el login de SIGPRO.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 2. Inicializamos el binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 3. Configuramos el clic del botón que definimos en el XML
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
        // Usamos lifecycleScope para que la petición corra en segundo plano
        lifecycleScope.launch {
            try {
                val request = LoginRequest(u, p)
                val response = ApiClient.apiService.login(request)

                if (response.isSuccessful) {
                    val body = response.body()
                    Toast.makeText(this@MainActivity, "¡Bienvenido a SIGPRO!", Toast.LENGTH_SHORT).show()

                    // Aquí es donde después haremos el salto a la pantalla de Inicio
                    // val intent = Intent(this@MainActivity, HomeActivity::class.java)
                    // startActivity(intent)
                } else {
                    Toast.makeText(this@MainActivity, "Matrícula o contraseña incorrecta", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                // Si el servidor de IntelliJ está apagado o la IP está mal, caerá aquí
                Toast.makeText(this@MainActivity, "Error de conexión: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}