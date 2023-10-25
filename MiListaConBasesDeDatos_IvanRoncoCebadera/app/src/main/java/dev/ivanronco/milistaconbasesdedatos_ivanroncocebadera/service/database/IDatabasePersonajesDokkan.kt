package dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera.service.database

interface IDatabasePersonajesDokkan <T, ID> {
    fun getAll(): List<T>
    fun addPersonaje(personaje: T): T
    fun updatePersonaje(personaje: T): T
    fun deleteById(id: ID): Boolean
}