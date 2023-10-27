package dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera.adapter

import dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera.models.DokkanCaracter

interface OnCaracterClickListener {
    fun update(personaje: DokkanCaracter)
    fun updatePersonaje(personaje: DokkanCaracter)
    fun deletePersonaje(personaje: DokkanCaracter)
}