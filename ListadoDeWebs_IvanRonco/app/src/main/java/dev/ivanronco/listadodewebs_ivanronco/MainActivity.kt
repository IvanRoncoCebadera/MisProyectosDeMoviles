package dev.ivanronco.listadodewebs_ivanronco

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import dev.ivanronco.listadodewebs_ivanronco.adapter.WebAdapter
import dev.ivanronco.listadodewebs_ivanronco.databinding.ActivityMainBinding
import dev.ivanronco.listadodewebs_ivanronco.models.PaginaWeb
import dev.ivanronco.listadodewebs_ivanronco.services.storage.paginasWeb.PaginasStorageServiceImpl

class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding

    private lateinit var webAdapter: WebAdapter

    private lateinit var webStorageCSVImpl: PaginasStorageServiceImpl

    private lateinit var paginas: MutableList<PaginaWeb>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpRecyclerView()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpRecyclerView() {
        webStorageCSVImpl = PaginasStorageServiceImpl(this)

        paginas = webStorageCSVImpl.importData()

        webAdapter = WebAdapter(paginas)

        binding.listaPaginasWeb.apply {
            setHasFixedSize(true)
            adapter = webAdapter
            layoutManager = LinearLayoutManager(context)
        }

        binding.editTextBuscadorWeb.addTextChangedListener {
            aplicarBuscador(it)
        }

        binding.buttonBorrar.setOnClickListener {
            if(nadieEstaMarcado()){
                Toast.makeText(this, "No se ha marcado ninguna página a borrar!!!", Toast.LENGTH_SHORT).show()
            }else{
                paginas.removeAll { it.isChecked }

                webAdapter.notifyDataSetChanged()

                webStorageCSVImpl.exportData(paginas)
            }
        }

        binding.buttonAAdir.setOnClickListener {
            val intent = Intent(this, AddWebActivity::class.java)
            startActivity(intent)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun aplicarBuscador(text: Editable?) {

        val paginasFiltradas = paginas.filter {
            it.nombre.lowercase().contains(
                text.toString().lowercase()
            )
        }.toMutableList()

        webAdapter.filtrarPaginas(paginasFiltradas)
    }

    private fun nadieEstaMarcado(): Boolean {
        return webAdapter.paginas.filter { it.isChecked }.isEmpty()
    }
}