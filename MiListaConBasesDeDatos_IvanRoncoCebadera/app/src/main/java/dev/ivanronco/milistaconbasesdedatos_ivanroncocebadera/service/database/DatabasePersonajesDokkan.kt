package dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera.service.database

import dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera.config.ConfigApp
import dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera.models.DokkanCaracter

class DatabasePersonajesDokkan(
    private val config: ConfigApp
): IDatabasePersonajesDokkan<DokkanCaracter, Long> {
    override fun getAll(): List<DokkanCaracter> {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: Long): Boolean {
        TODO("Not yet implemented")
    }

    override fun updatePersonaje(personaje: DokkanCaracter): DokkanCaracter {
        TODO("Not yet implemented")
    }

    override fun addPersonaje(personaje: DokkanCaracter): DokkanCaracter {
        TODO("Not yet implemented")
    }
}