package dev.ivanronco.calculadoraivan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import dev.ivanronco.calculadoraivan.databinding.ActivityMainBinding
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var operationMode: OperationMode = OperationMode.SUMAR

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBindings()
    }

    private fun initBindings() {
        binding.boton0.setOnClickListener {
            colocarElementoEnLaPantalla("0")
        }
        binding.boton1.setOnClickListener {
            colocarElementoEnLaPantalla("1")
        }
        binding.boton2.setOnClickListener {
            colocarElementoEnLaPantalla("2")
        }
        binding.boton3.setOnClickListener {
            colocarElementoEnLaPantalla("3")
        }
        binding.boton4.setOnClickListener {
            colocarElementoEnLaPantalla("4")
        }
        binding.boton5.setOnClickListener {
            colocarElementoEnLaPantalla("5")
        }
        binding.boton6.setOnClickListener {
            colocarElementoEnLaPantalla("6")
        }
        binding.boton7.setOnClickListener {
            colocarElementoEnLaPantalla("7")
        }
        binding.boton8.setOnClickListener {
            colocarElementoEnLaPantalla("8")
        }
        binding.boton9.setOnClickListener {
            colocarElementoEnLaPantalla("9")
        }

        binding.botonDividir.setOnClickListener {
            colocarElementoEnLaPantalla("  /  ")
            operationMode = OperationMode.DIVIDIR
        }
        binding.botonSumar.setOnClickListener {
            colocarElementoEnLaPantalla("  +  ")
            operationMode = OperationMode.SUMAR
        }
        binding.botonRestar.setOnClickListener {
            colocarElementoEnLaPantalla("  -  ")
            operationMode = OperationMode.RESTAR
        }
        binding.botonMultiplicar.setOnClickListener {
            colocarElementoEnLaPantalla("  *  ")
            operationMode = OperationMode.MULTIPLICAR
        }

        binding.botonOperacion.setOnClickListener {
            realizarLaOperacionPedida()
        }

        binding.botonBorrarTodo.setOnClickListener {
            borrarTodoElContenidoDeLaPantalla()
        }
        binding.botonBorrarCaracter.setOnClickListener {
            borrarElUltimoCaracterAñadido()
        }
    }

    private fun borrarElUltimoCaracterAñadido() {
        var textoPantalla = binding.pantallaVisualizacion.text.toString()
        val textoSize = textoPantalla.length
        val lastCaracter = textoPantalla.lastOrNull()

        if(lastCaracter != null){
            if(lastCaracter == ' '){
                textoPantalla = textoPantalla.removeRange(textoSize-5, textoSize)
            }else{
                if(
                    lastCaracter == '+' ||
                    lastCaracter == '-' ||
                    lastCaracter == '*' ||
                    lastCaracter == '/'
                ){
                    textoPantalla = textoPantalla.removeRange(textoSize-3, textoSize)
                }else{
                    textoPantalla = textoPantalla.removeRange(textoSize-1, textoSize)
                }
            }
        }

        binding.pantallaVisualizacion.setText(textoPantalla)
    }

    private fun borrarTodoElContenidoDeLaPantalla() {
        binding.pantallaVisualizacion.setText("")
    }

    private fun realizarLaOperacionPedida() {

        when(operationMode){
            OperationMode.SUMAR -> {
                sumarOperandos()
            }
            OperationMode.RESTAR -> {
                restarOperandos()
            }
            OperationMode.MULTIPLICAR -> {
                multiplicacionOperandos()
            }
            else -> {
                divisionOperandos()
            }
        }
    }

    private fun divisionOperandos() {
        try {
            val (op1, op2) = binding.pantallaVisualizacion.text.toString().split("/").map { it.trim().toInt() }

            if(op2 == 0){
                Toast.makeText(this, "No se puede dividir entre 0!!!", Toast.LENGTH_SHORT).show()
                borrarElUltimoCaracterAñadido()
            }else{
                val division = op1 / op2

                Toast.makeText(this, "El resultado de la division es: $division", Toast.LENGTH_SHORT).show()
            }
        }catch (e: Exception){
            Toast.makeText(this, "No hay ninguna operación a realizar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun multiplicacionOperandos() {
        try {
            val (op1, op2) = binding.pantallaVisualizacion.text.toString().split("*").map { it.trim().toInt() }

            val multiplicacion = op1 * op2

            Toast.makeText(this, "El resultado de la multiplicacion es: $multiplicacion", Toast.LENGTH_SHORT).show()
        }catch (e: Exception){
            Toast.makeText(this, "No hay ninguna operación a realizar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun restarOperandos() {
        try {
            val (op1, op2) = binding.pantallaVisualizacion.text.toString().split("-").map { it.trim().toInt() }

            val resta = op1 - op2

            Toast.makeText(this, "El resultado de la resta es: $resta", Toast.LENGTH_SHORT).show()
        }catch (e: Exception){
            Toast.makeText(this, "No hay ninguna operación a realizar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sumarOperandos() {
        try {
            val (op1, op2) = binding.pantallaVisualizacion.text.toString().split("+").map { it.trim().toInt() }

            val suma = op1 + op2

            Toast.makeText(this, "El resultado de la suma es: $suma", Toast.LENGTH_SHORT).show()
        }catch (e: Exception){
            Toast.makeText(this, "No hay ninguna operación a realizar", Toast.LENGTH_SHORT).show()
        }
    }

    fun colocarElementoEnLaPantalla(elemento: String){
        var textoDePantalla = binding.pantallaVisualizacion.text.toString()

        if(
            elemento == "+" ||
            elemento == "-" ||
            elemento == "*" ||
            elemento == "/")
        {
            if(
                textoDePantalla.split("+").size == 2 ||
                textoDePantalla.split("-").size == 2 ||
                textoDePantalla.split("*").size == 2 ||
                textoDePantalla.split("/").size == 2
            )
            {
                Toast.makeText(this, "No puedes tener más de una operación a la vez!!!", Toast.LENGTH_SHORT).show()
            }
        }else {
            textoDePantalla += "$elemento"
        }

        binding.pantallaVisualizacion.setText(textoDePantalla)
    }
}

enum class OperationMode {
    SUMAR, RESTAR, DIVIDIR, MULTIPLICAR
}
