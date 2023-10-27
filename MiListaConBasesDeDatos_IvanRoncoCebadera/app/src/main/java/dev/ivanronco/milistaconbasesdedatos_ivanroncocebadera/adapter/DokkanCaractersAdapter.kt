package dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera.R
import dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera.databinding.DokkanCaracterViewBinding
import dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera.models.DokkanCaracter

class DokkanCaractersAdapter(
    var listaPersonajes: MutableList<DokkanCaracter>,
    private val listener: OnCaracterClickListener
): RecyclerView.Adapter<DokkanCaractersAdapter.DokkanCaractersViewHolder>() {

    fun setList(listaPersonajes: MutableList<DokkanCaracter>){
        this.listaPersonajes = listaPersonajes
        notifyDataSetChanged()
    }

    fun addPersonaje(personaje: DokkanCaracter){
        listaPersonajes.add(personaje)
        notifyItemChanged(itemCount-1)
    }

    fun updatePersonaje(personaje: DokkanCaracter){
        var indice = 0
        for (i in listaPersonajes.indices){
            if(listaPersonajes[i].id == personaje.id){
                indice = i
                listaPersonajes[i] = personaje
                break
            }
        }
        notifyItemChanged(indice)
    }

    fun deletePersonaje(id: Long){
        listaPersonajes.removeAll { pj -> pj.id.equals(id) }
        notifyDataSetChanged()
    }

    inner class DokkanCaractersViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding: DokkanCaracterViewBinding = DokkanCaracterViewBinding.bind(view)

        @SuppressLint("ResourceAsColor")
        fun bind(personaje: DokkanCaracter){
            if(personaje.esFavorito){
                binding.iconoPersonajeFavorito.setImageResource(R.drawable.estrella_rellena)
            }else{
                binding.iconoPersonajeFavorito.setImageResource(R.drawable.estrella_vacia)
            }

            binding.textoNombrePersonaje.setText(personaje.nombre)

            Glide.with(binding.imagenPersonaje.context)
                .load(if(personaje.imagen.isEmpty()) R.drawable.ic_launcher_foreground else personaje.imagen) //Le pongo un como valor por defecto
                .into(binding.imagenPersonaje)

            binding.cartaPersonaje.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, personaje.tipoPersonaje.color))

            setUpListeners(personaje)
        }

        private fun setUpListeners(personaje: DokkanCaracter) {
            binding.iconoPersonajeFavorito.setOnClickListener {
                Log.i("Estrella", personaje.toString())
                if(personaje.esFavorito){
                    binding.iconoPersonajeFavorito.setImageResource(R.drawable.estrella_vacia)
                    personaje.esFavorito = false
                    listener.update(personaje)
                }else{
                    binding.iconoPersonajeFavorito.setImageResource(R.drawable.estrella_rellena)
                    personaje.esFavorito = true
                    listener.update(personaje)
                }
                Log.i("Estrella", personaje.toString())
                Log.i("Estrella", "")
            }

            binding.botonMenuDesplegable.setOnClickListener { view ->
                val popupMenu = PopupMenu(binding.root.context, view)
                popupMenu.menuInflater.inflate(R.menu.menu_caracter, popupMenu.menu)

                popupMenu.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.itemActualizador -> {
                            listener.updatePersonaje(personaje)
                            true
                        }

                        R.id.itemBorrador -> {
                            AlertDialog.Builder(binding.root.context).apply {
                                setTitle("¿Seguro que quieres eliminar este registro?")
                                setPositiveButton("Aceptar") { _, _ ->
                                    listener.deletePersonaje(personaje)
                                }
                                setNegativeButton("Cancelar") { _, _ ->
                                    Toast.makeText(
                                        binding.root.context,
                                        "Se ha cancelado la operación!!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }.create().show()
                            true
                        }

                        else -> false
                    }
                }

                popupMenu.show()
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