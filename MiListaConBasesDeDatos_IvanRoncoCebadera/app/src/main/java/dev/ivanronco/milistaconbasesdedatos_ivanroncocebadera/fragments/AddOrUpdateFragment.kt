package dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera.MainActivity
import dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera.R
import dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera.databinding.FragmentAddOrUpdateBinding
import dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera.models.DokkanCaracter
import dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera.models.TipoPersonaje

class AddOrUpdateFragment : Fragment() {

    private lateinit var binding: FragmentAddOrUpdateBinding

    private lateinit var imagenSeleccionadaUri: String

    private lateinit var modoOperacion: ModoOperacion

    enum class ModoOperacion {
        AÑADIR, ACTUALIZAR
    }

    companion object {
        fun newInstance(data: DokkanCaracter?): AddOrUpdateFragment {
            val fragment = AddOrUpdateFragment()
            val args = Bundle()
            args.putSerializable("personaje", data)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddOrUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpBindings()
    }

    private fun setUpBindings() {
        val personaje: DokkanCaracter? = arguments?.getSerializable("personaje") as DokkanCaracter? //Funciona!

        //De esta forma sabermos si vamos a actualizar
        if(personaje != null){ //Actualizar!!!
            modoOperacion = ModoOperacion.ACTUALIZAR

            binding.vistaFragmento.setBackgroundColor(ContextCompat.getColor(binding.root.context, personaje.tipoPersonaje.color))

            binding.checkFavorito.isChecked = personaje.esFavorito

            Glide.with(binding.imagenPersonaje.context)
                .load(if(personaje.imagen.isEmpty()) R.drawable.ic_launcher_foreground else personaje.imagen) //Le pongo un como valor por defecto
                .into(binding.imagenPersonaje)

            binding.textoNombrePersonaje.setText(personaje.nombre)

            when(personaje.tipoPersonaje){
                TipoPersonaje.PHY -> binding.PHY.isChecked = true
                TipoPersonaje.AGL -> binding.AGL.isChecked = true
                TipoPersonaje.TEQ -> binding.TEQ.isChecked = true
                TipoPersonaje.STR -> binding.STR.isChecked = true
                else -> binding.INT.isChecked = true
            }

            imagenSeleccionadaUri = personaje.imagen
        }else{
            modoOperacion = ModoOperacion.AÑADIR

            imagenSeleccionadaUri = ""
        }

        Glide.with(binding.imagenPersonaje.context)
            .load(if(imagenSeleccionadaUri.isEmpty()) R.drawable.ic_launcher_foreground else imagenSeleccionadaUri) //Le pongo un como valor por defecto
            .into(binding.imagenPersonaje)

        binding.PHY.setOnClickListener {
            binding.vistaFragmento.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.Phy))
        }
        binding.STR.setOnClickListener {
            binding.vistaFragmento.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.Str))
        }
        binding.AGL.setOnClickListener {
            binding.vistaFragmento.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.Agl))
        }
        binding.TEQ.setOnClickListener {
            binding.vistaFragmento.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.Teq))
        }
        binding.INT.setOnClickListener {
            binding.vistaFragmento.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.Int))
        }

        binding.imagenPersonaje.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 1)
            intent.data
        }

        binding.botonCancelar.setOnClickListener {
            Toast.makeText(binding.root.context, "Se ha cancela la operación!!", Toast.LENGTH_SHORT).show()

            volverALaLista()
        }

        binding.botonAceptar.setOnClickListener {

            val personaje = crearPersonajeConLosDatos()

            val mainActivity = requireActivity() as MainActivity

            if(personaje != null){

                if(modoOperacion == ModoOperacion.AÑADIR){
                    mainActivity.addNewPersonaje(personaje)
                }else{
                    mainActivity.update(personaje)
                }
            }else{
                mainActivity.deshabilitarBotonAñadirYRecycler()
                Toast.makeText(binding.root.context, "Se ha cancela la operación!!", Toast.LENGTH_SHORT).show()
            }

            volverALaLista()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                imagenSeleccionadaUri = data.data.toString()
                binding.imagenPersonaje.setImageURI(Uri.parse(imagenSeleccionadaUri))
            }
        }
    }

    private fun crearPersonajeConLosDatos(): DokkanCaracter? {
        val nombre = binding.textoNombrePersonaje.text.toString()

        if (nombre.isEmpty()){
            Toast.makeText(binding.root.context, "El nombre no puede estar vacio!!!", Toast.LENGTH_SHORT).show()
            return null
        }

        val esFavorito = binding.checkFavorito.isChecked

        var tipoPersonaje = TipoPersonaje.DEFAULT

        if(binding.AGL.isChecked){
            tipoPersonaje = TipoPersonaje.AGL
        }else{
            if(binding.TEQ.isChecked){
                tipoPersonaje = TipoPersonaje.TEQ
            }else{
                if(binding.STR.isChecked){
                    tipoPersonaje = TipoPersonaje.STR
                }else{
                    if(binding.PHY.isChecked){
                        tipoPersonaje = TipoPersonaje.PHY
                    }else{
                        if(binding.INT.isChecked){
                            tipoPersonaje = TipoPersonaje.INT
                        }else{
                            Toast.makeText(binding.root.context, "Debes seleccionar uno de los tipos posibles!!!", Toast.LENGTH_SHORT).show()
                            return null
                        }
                    }
                }
            }
        }

        return if(modoOperacion == ModoOperacion.AÑADIR){
            DokkanCaracter(nombre = nombre, imagen = imagenSeleccionadaUri, esFavorito = esFavorito, tipoPersonaje = tipoPersonaje)
        }else{
            DokkanCaracter(id = (arguments?.getSerializable("personaje") as DokkanCaracter).id, nombre = nombre, imagen = imagenSeleccionadaUri, esFavorito = esFavorito, tipoPersonaje = tipoPersonaje)
        }

    }

    private fun volverALaLista() {
        val mainActivity = requireActivity() as MainActivity
        mainActivity.rehabilitarBotonAñadirYRecycler()
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.remove(this) // Elimina el fragmento actual
        fragmentTransaction.commit()
    }
}