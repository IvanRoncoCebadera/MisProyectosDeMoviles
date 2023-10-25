package dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera.models

import java.io.Serializable

data class DokkanCaracter(
    val id: Long = giveNextId(),
    val nombre: String = "",
    val tipoPersonaje: TipoPersonaje = TipoPersonaje.DEFAULT,
    var esFavorito: Boolean = false,
    val imagen: String = ""
): Serializable{
    companion object{
        var contador = 1L;
        private fun giveNextId(): Long{
            return contador++;
        }
    }

    override fun hashCode(): Int {
        return (imagen.hashCode() + tipoPersonaje.hashCode() + nombre.hashCode())
    }
}