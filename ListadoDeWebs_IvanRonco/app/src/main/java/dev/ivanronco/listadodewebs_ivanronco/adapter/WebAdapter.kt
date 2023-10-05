package dev.ivanronco.listadodewebs_ivanronco.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.ivanronco.listadodewebs_ivanronco.R
import dev.ivanronco.listadodewebs_ivanronco.databinding.WebViewBinding
import dev.ivanronco.listadodewebs_ivanronco.models.PaginaWeb
import java.lang.Exception
import java.lang.RuntimeException

class WebAdapter(
    var paginas: MutableList<PaginaWeb> = mutableListOf()
):
    RecyclerView.Adapter<WebAdapter.WebViewHolder>()
{

    @SuppressLint("NotifyDataSetChanged")
    fun filtrarPaginas(paginasFiltradas: MutableList<PaginaWeb>){
        paginas = paginasFiltradas
        notifyDataSetChanged()
    }

    inner class WebViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val binding = WebViewBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WebAdapter.WebViewHolder {
        return WebViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.web_view, //Le paso el layout de la vista de la nota!!!
                parent,
                false //Para no encajarlo a la vista principal???
            )
        )
    }

    override fun getItemCount(): Int {
        return paginas.size
    }

    override fun onBindViewHolder(holder: WebViewHolder, position: Int) {
        val currentWeb = paginas[position]

        with(holder){
            binding.nombreWeb.text = currentWeb.nombre
            binding.checkBoxBorrar.isChecked = currentWeb.isChecked

            try {
                Glide.with(binding.iconoWeb.context)
                    .load(if(currentWeb.imagePath.isEmpty()) throw RuntimeException() else currentWeb.imagePath)
                    .into(binding.iconoWeb)
            }catch (e: Exception){
                binding.iconoWeb.setImageResource(R.drawable.ic_launcher_foreground)
            }

            binding.checkBoxBorrar.setOnCheckedChangeListener { _, isChecked ->
                currentWeb.isChecked = isChecked // Actualizamos el estado isChecked
            }

            binding.paginaWeb.setOnClickListener {
                try {
                    val intent = Intent(Intent.ACTION_VIEW, currentWeb.uri)

                    startActivity(it.context, intent, Bundle.EMPTY)
                }catch (e: Exception){
                    Toast.makeText(it.context, "Parece que el enlace de la página web no funciona, recomendamos que borres el registro y lo vuelvas a hacer!!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    class NoteDiffCallback: DiffUtil.ItemCallback<PaginaWeb>(){
        override fun areItemsTheSame(oldItem: PaginaWeb, newItem: PaginaWeb): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: PaginaWeb, newItem: PaginaWeb): Boolean = oldItem == newItem
    }
}