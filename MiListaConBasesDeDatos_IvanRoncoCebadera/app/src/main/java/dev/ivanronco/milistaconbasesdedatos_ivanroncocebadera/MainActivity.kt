package dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera.adapter.DokkanCaractersAdapter
import dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera.adapter.OnCaracterClickListener
import dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera.databinding.ActivityMainBinding
import dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera.fragments.AddOrUpdateFragment
import dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera.models.DokkanCaracter
import dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera.repositories.RepositoryPersonajesDokkan
import dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera.service.database.DokkanCaracterApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity(), OnCaracterClickListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var caractersAdapter: DokkanCaractersAdapter
    private lateinit var mLayoutManager: LinearLayoutManager

    private lateinit var repo: RepositoryPersonajesDokkan

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //repo = RepositoryPersonajesDokkan(
        //    mutableListOf(
        //        DokkanCaracter(nombre = "Goku (base)", tipoPersonaje = TipoPersonaje.INT, esFavorito = false, imagen = "content://com.google.android.apps.photos.contentprovider/-1/1/content%3A%2F%2Fmedia%2Fexternal%2Fimages%2Fmedia%2F1000000034/ORIGINAL/NONE/image%2Fjpeg/249860352"),
        //        DokkanCaracter(nombre = "Gohan (ultimate)", tipoPersonaje = TipoPersonaje.PHY, esFavorito = true, imagen = "")
        //   )
        //)

        setBindings()
    }

    private fun setBindings() {
        setUpRecyclerView()

        binding.botonAAdir.setOnClickListener{
            addPersonaje()
        }
    }

    private fun setUpRecyclerView() {
        caractersAdapter = DokkanCaractersAdapter(
            mutableListOf(), this
        )

        getListDokkanCaracters()

        mLayoutManager = LinearLayoutManager(binding.root.context)

        binding.listaDePersonajes.apply {
            setHasFixedSize(true)
            adapter = caractersAdapter
            layoutManager = mLayoutManager
        }
    }

    private fun getListDokkanCaracters() {
        runBlocking {
            launch(Dispatchers.IO) {
                val listNote = DokkanCaracterApplication.database.dokkanCaracterDao().getAll()

                caractersAdapter.setList(listNote)
            }
        }
    }

    fun deshabilitarBotonAñadirYRecycler(){
        binding.botonAAdir.isVisible = false
        binding.listaDePersonajes.isVisible = false;
    }

    fun rehabilitarBotonAñadirYRecycler(){
        binding.botonAAdir.isVisible = true
        binding.listaDePersonajes.isVisible = true;
    }

    fun addPersonaje(){
        deshabilitarBotonAñadirYRecycler()

        val fragment = AddOrUpdateFragment.newInstance(null)

        supportFragmentManager.beginTransaction()
            .replace(R.id.contenedorFragmentos, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun addNewPersonaje(personaje: DokkanCaracter){
        //repo.addPersonaje(personaje)
        //caractersAdapter.notifyItemChanged(caractersAdapter.itemCount-1)

        runBlocking {
            launch(Dispatchers.IO) {
                DokkanCaracterApplication.database.dokkanCaracterDao().addPersonaje(personaje) //Añado a la base de datos
            }
        }

        caractersAdapter.addPersonaje(personaje)
    }

    override fun update(personaje: DokkanCaracter){
        //repo.deleteById(personaje.id)
        //repo.addPersonaje(personaje)
        //caractersAdapter.listaPersonajes = repo.getAll()

        runBlocking {
            launch(Dispatchers.IO) {
                DokkanCaracterApplication.database.dokkanCaracterDao().updatePersonaje(personaje)
            }
        }

        caractersAdapter.updatePersonaje(personaje)
    }

    override fun updatePersonaje(personaje: DokkanCaracter) {
        deshabilitarBotonAñadirYRecycler()

        val fragment = AddOrUpdateFragment.newInstance(personaje)

        supportFragmentManager.beginTransaction()
            .replace(R.id.contenedorFragmentos, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun deletePersonaje(personaje: DokkanCaracter){
        //repo.deleteById(id)

        //lifecycleScope.launch {
        //    DokkanCaracterApplication.database.dokkanCaracterDao().delete(personaje)
        //}

        if (!personaje.esFavorito){
            runBlocking {
                launch(Dispatchers.IO) {
                    DokkanCaracterApplication.database.dokkanCaracterDao().delete(personaje)

                    Log.i("Delete", "Sobre la BBDD")
                }
            }

            caractersAdapter.deletePersonaje(personaje.id)
        }else{
            Toast.makeText(this, "No se puede eliminar un personaje favorito!!", Toast.LENGTH_SHORT).show()
        }
    }
}