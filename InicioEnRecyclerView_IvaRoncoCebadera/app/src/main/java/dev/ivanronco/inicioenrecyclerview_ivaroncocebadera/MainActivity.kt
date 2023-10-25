package dev.ivanronco.inicioenrecyclerview_ivaroncocebadera

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import dev.ivanronco.inicioenrecyclerview_ivaroncocebadera.adapter.PersonOnClickListener
import dev.ivanronco.inicioenrecyclerview_ivaroncocebadera.adapter.PersonaListAdapter
import dev.ivanronco.inicioenrecyclerview_ivaroncocebadera.databinding.ActivityMainBinding
import dev.ivanronco.inicioenrecyclerview_ivaroncocebadera.models.Persona

class MainActivity : AppCompatActivity(), PersonOnClickListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var personasAdapter: PersonaListAdapter

    private lateinit var mLayoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {

        val listaPersonas = (245..255).map {
            Persona(
                "Iván Ronco Cebadera $it",
                "https://loremflickr.com/320/240/cats/?lock = $it"
            )
        }

        personasAdapter = PersonaListAdapter(listaPersonas, this@MainActivity)

        mLayoutManager = GridLayoutManager(this, 2)

        binding.recyclerViewPersonas.apply {
            setHasFixedSize(true)
            adapter = personasAdapter
            layoutManager = mLayoutManager
        }
    }

    override fun onClickPerson(persona: Persona): Persona{
        Toast.makeText(this, "Hola!. Has pulsado sobre: ${persona.nombre}", Toast.LENGTH_SHORT).show()
        return persona
    }
}