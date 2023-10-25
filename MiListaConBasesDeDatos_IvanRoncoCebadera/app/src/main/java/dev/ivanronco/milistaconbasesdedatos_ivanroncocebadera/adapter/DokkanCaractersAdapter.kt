package dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera.R
import dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera.databinding.DokkanCaracterViewBinding
import dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera.models.DokkanCaracter

class DokkanCaractersAdapter(
    var listaPersonajes: List<DokkanCaracter>,
    private val listener: OnCaracterClickListener
): RecyclerView.Adapter<DokkanCaractersAdapter.DokkanCaractersViewHolder>() {

    inner class DokkanCaractersViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding: DokkanCaracterViewBinding = DokkanCaracterViewBinding.bind(view)

        @SuppressLint("ResourceAsColor")
        fun bind(personaje: DokkanCaracter){
            if(personaje.esFavorito){
                binding.iconoPersonajeFavorito.setImageResource(R.drawable.estrella_rellena)
            } //No hay else, porque por defecto, ya esta la estrella vacía!!

            binding.textoNombrePersonaje.setText(personaje.nombre)

            Glide.with(binding.imagenPersonaje.context)
                .load(if(personaje.imagen.isEmpty()) R.drawable.ic_launcher_foreground else personaje.imagen) //Le pongo un como valor por defecto
                .into(binding.imagenPersonaje)

            binding.cartaPersonaje.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, personaje.tipoPersonaje.color))

            setUpListeners(personaje)
        }

        private fun setUpListeners(personaje: DokkanCaracter) {
            binding.iconoPersonajeFavorito.setOnClickListener {
                if(personaje.esFavorito){
                    binding.iconoPersonajeFavorito.setImageResource(R.drawable.estrella_vacia)
                    personaje.esFavorito = false
                }else{
                    binding.iconoPersonajeFavorito.setImageResource(R.drawable.estrella_rellena)
                    personaje.esFavorito = true
                }
            }

            binding.iconoBorrarPersonaje.setOnClickListener {
                AlertDialog.Builder(binding.root.context).apply {
                    setTitle("¿Seguro que quieres eliminar este registro?")
                    setPositiveButton("Aceptar"){ _ ,_ ->
                        listaPersonajes = listener.deletePersonaje(personaje.id)
                        notifyDataSetChanged()
                    }
                    setNegativeButton("Cancelar"){ _, _ ->
                        Toast.makeText(binding.root.context, "Se ha cancelado la operación!!", Toast.LENGTH_SHORT).show()
                    }
                }.create().show()
            }

            binding.zonaInteractuableParaEditar.setOnClickListener {
                listener.updatePersonaje(personaje)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DokkanCaractersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.dokkan_caracter_view,
            parent,
            false
        )
        return DokkanCaractersViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listaPersonajes.size
    }

    override fun onBindViewHolder(holder: DokkanCaractersViewHolder, position: Int) {
        val currentCaracter = listaPersonajes.get(position)

        with(holder){
            bind(currentCaracter)
        }
    }

}