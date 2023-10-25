package dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera.adapter.DokkanCaractersAdapter
import dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera.adapter.OnCaracterClickListener
import dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera.databinding.ActivityMainBinding
import dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera.fragments.AddOrUpdateFragment
import dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera.models.DokkanCaracter
import dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera.models.TipoPersonaje
import dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera.repositories.RepositoryPersonajesDokkan

class MainActivity : AppCompatActivity(), OnCaracterClickListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var caractersAdapter: DokkanCaractersAdapter
    private lateinit var mLayoutManager: LinearLayoutManager

    private lateinit var repo: RepositoryPersonajesDokkan

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repo = RepositoryPersonajesDokkan(
            mutableListOf(
                DokkanCaracter(nombre = "Goku (base)", tipoPersonaje = TipoPersonaje.INT, esFavorito = false, imagen = "content://com.google.android.apps.photos.contentprovider/-1/1/content%3A%2F%2Fmedia%2Fexternal%2Fimages%2Fmedia%2F1000000034/ORIGINAL/NONE/image%2Fjpeg/249860352"),
                DokkanCaracter(nombre = "Gohan (ultimate)", tipoPersonaje = TipoPersonaje.PHY, esFavorito = true, imagen = "")
            )
        )

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
            repo.getAll(), this
        )

        mLayoutManager = LinearLayoutManager(binding.root.context)

        binding.listaDePersonajes.apply {
            setHasFixedSize(true)
            adapter = caractersAdapter
            layoutManager = mLayoutManager
        }
    }

    fun deshabilitarBotonAñadir(){
        binding.botonAAdir.isVisible = false
    }

    fun rehabilitarBotonAñadir(){
        binding.botonAAdir.isVisible = true
    }

    fun addPersonaje(){
        deshabilitarBotonAñadir()

        val fragment = AddOrUpdateFragment.newInstance(null)

        supportFragmentManager.beginTransaction()
            .replace(R.id.contenedorFragmentos, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun addNewPersonaje(personaje: DokkanCaracter){
        repo.addPersonaje(personaje)
        caractersAdapter.notifyDataSetChanged()

        Log.i("Comprobar", repo.getAll().joinToString(separator = " | "))
    }

    fun update(personaje: DokkanCaracter){
        repo.deleteById(personaje.id)
        repo.addPersonaje(personaje)
        caractersAdapter.listaPersonajes = repo.getAll()
        caractersAdapter.notifyDataSetChanged()
    }

    override fun updatePersonaje(personaje: DokkanCaracter) {
        deshabilitarBotonAñadir()

        val fragment = AddOrUpdateFragment.newInstance(personaje)

        supportFragmentManager.beginTransaction()
            .replace(R.id.contenedorFragmentos, fragment)
            .addToBackStack(null)
            .commit()

        Log.i("Comprobar", repo.getAll().joinToString(separator = " | "))
    }

    override fun deletePersonaje(id: Long): List<DokkanCaracter> {
        repo.deleteById(id)
        return repo.getAll()
    }
}