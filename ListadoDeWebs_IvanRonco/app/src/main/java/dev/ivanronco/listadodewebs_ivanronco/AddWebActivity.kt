package dev.ivanronco.listadodewebs_ivanronco

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import dev.ivanronco.listadodewebs_ivanronco.databinding.ActivityAddWebBinding
import dev.ivanronco.listadodewebs_ivanronco.models.PaginaWeb
import dev.ivanronco.listadodewebs_ivanronco.services.storage.paginasWeb.PaginasStorageServiceImpl
import java.lang.Exception
import java.net.URL

class AddWebActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddWebBinding

    private lateinit var webCSVStorageServiceImpl: PaginasStorageServiceImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddWebBinding.inflate(layoutInflater)
        setContentView(binding.root)

        webCSVStorageServiceImpl = PaginasStorageServiceImpl(this)

        setBindings()
    }

    private fun setBindings() {
        binding.buttonCancelar.setOnClickListener {
            cambiarAMainActivity()
        }

        binding.buttonAceptar.setOnClickListener {
            try {
                val nombre = binding.textNombreWeb.text.toString()
                val uri = Uri.parse(binding.textURLWeb.text.toString())
                var imageDrawable= binding.textEnlaceImagenWeb.text.toString()


                val nuevaPagina = PaginaWeb(nombre = nombre, uri = uri, imagePath = imageDrawable)

                val listaPaginas = webCSVStorageServiceImpl.importData()
                listaPaginas.add(nuevaPagina)

                webCSVStorageServiceImpl.exportData(listaPaginas)
            }catch (e: Exception){
                Toast.makeText(this, "Alguno de los aprtados entregados no es correcto!!", Toast.LENGTH_SHORT).show()
            }finally {
                cambiarAMainActivity()
            }
            }
    }

    private fun cambiarAMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}