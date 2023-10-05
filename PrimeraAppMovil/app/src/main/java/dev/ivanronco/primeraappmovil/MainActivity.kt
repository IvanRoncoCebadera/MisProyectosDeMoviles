package dev.ivanronco.primeraappmovil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import dev.ivanronco.primeraappmovil.databinding.ActivityMainBinding
import dev.ivanronco.primeraappmovil.databinding.FragmentRegisterBinding
import dev.ivanronco.primeraappmovil.databinding.LayoutLinearBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: LayoutLinearBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LayoutLinearBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.botonLogin.setOnClickListener {
           Toast.makeText(this, "Has pulsado en botón de login", Toast.LENGTH_SHORT).show()
            login()
        }

        binding.botonRegistrar.setOnClickListener {
            registrar()
        }
    }

    private fun registrar() {
        val intent: Intent = Intent(this, FragmentRegisterBinding::class.java)
        startActivity(intent)
    }

    private fun login() {
        TODO("Not yet implemented")
    }
}