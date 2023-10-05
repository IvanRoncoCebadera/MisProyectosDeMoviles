package dev.ivanronco.calculadoraderebajas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import dev.ivanronco.calculadoraderebajas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val posiblesPorcentajes = arrayOf(10, 15 ,21)
    var posicionProcentajeElegido = 0 //Valor por defecto el 1
    private val posiblesRepresentaciones = arrayOf(true, false)
    var posicionRepresentacionElegida = 0 //Valor por defecto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setBindings()
    }

    private fun setBindings() {

        binding.radioDePorcentajes.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.botonPorcentaje10 ->{
                    posicionProcentajeElegido = 0
                }
                R.id.botonPorcentaje15 ->{
                    posicionProcentajeElegido = 1
                }
                R.id.botonPorcentaje21 ->{
                    posicionProcentajeElegido = 2
                }
            }
        }

        binding.radioDeRepresentacion.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.botonResEntero ->{
                    posicionRepresentacionElegida = 0
                }
                R.id.botonResDecimal ->{
                    posicionRepresentacionElegida = 1
                }
            }
        }

        binding.botonOperacion.setOnClickListener {
            operar()
        }
    }

    private fun operar() {
        val valor = binding.textoValorProducto.editText!!.text.toString().toDoubleOrNull()

        if(valor == null){
            Toast.makeText(this, "No has puesto ningún valor posibles para la operación!!", Toast.LENGTH_SHORT)
            binding.textoValorProducto.editText!!.setText("")
        }else{
            val porcentaje = posiblesPorcentajes[posicionProcentajeElegido]
            val tipoRepresentacion = posiblesRepresentaciones[posicionRepresentacionElegida]
            val res: Double = valor * porcentaje / 100

            if(tipoRepresentacion){
                binding.textoResFinal.setText("${res.toInt()}")
            }else{
                binding.textoResFinal.setText("$res")
            }
        }
    }
}