package dev.ivanronco.inicioenrecyclerview_ivaroncocebadera.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.ivanronco.inicioenrecyclerview_ivaroncocebadera.R
import dev.ivanronco.inicioenrecyclerview_ivaroncocebadera.databinding.ItemPersonaBinding
import dev.ivanronco.inicioenrecyclerview_ivaroncocebadera.models.Persona

class PersonaListAdapter(
    private val lista: List<Persona>,
    private val listener: PersonOnClickListener
): RecyclerView.Adapter<PersonaListAdapter.PersonasViewHolder>() {

    inner class PersonasViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding: ItemPersonaBinding = ItemPersonaBinding.bind(view)

        fun bind(persona: Persona){
            binding.textoNombre.text = persona.nombre

            Glide.with(binding.imagenPersona)
                .load(persona.imagen).centerCrop()//La centramos!!!
                .into(binding.imagenPersona)
        }

        fun setListener(persona: Persona){
            binding.root.setOnClickListener { listener.onClickPerson(persona) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonasViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_persona, parent, false)
        return PersonasViewHolder(view)
    }

    override fun onBindViewHolder(holder: PersonasViewHolder, position: Int) {
        val currentPersona = lista[position]

        with(holder){ // holder as ...
            bind(currentPersona)
            setListener(currentPersona)
        }

        //Si, como en este caso, solo tengo que hacer una cos, mejor esto:
        //holder.bind(currentPersona) //Pero bueno...
    }

    override fun getItemCount(): Int = lista.size
}