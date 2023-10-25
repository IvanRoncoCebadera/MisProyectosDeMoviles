package dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera.repositories

import dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera.models.DokkanCaracter

class RepositoryPersonajesDokkan(
    //private val database: IDatabasePersonajesDokkan<DokkanCaracter, Long>
    private val listaPersonajes: MutableList<DokkanCaracter>
): IPersonajesDokkanRepository<DokkanCaracter, Long> {

    override fun getAll(): List<DokkanCaracter> {
        return listaPersonajes
    }

    override fun deleteById(id: Long): Boolean {
        return listaPersonajes.removeIf { pj -> pj.id == id }
    }

    override fun addPersonaje(personaje: DokkanCaracter): DokkanCaracter {
        listaPersonajes.add(personaje)
        return personaje
    }

    override fun updatePersonaje(personaje: DokkanCaracter): DokkanCaracter {
        deleteById(personaje.id)
        return addPersonaje(personaje)
    }
}