package dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera.adapter

import dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera.models.DokkanCaracter

interface OnCaracterClickListener {
    fun updatePersonaje(personaje: DokkanCaracter)
    fun deletePersonaje(id: Long): List<DokkanCaracter>
}