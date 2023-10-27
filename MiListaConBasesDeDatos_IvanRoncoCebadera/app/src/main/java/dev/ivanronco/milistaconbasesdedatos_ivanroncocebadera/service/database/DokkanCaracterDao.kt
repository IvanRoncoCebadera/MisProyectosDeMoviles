package dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera.service.database

import androidx.room.*
import dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera.models.DokkanCaracter

@Dao
interface DokkanCaracterDao {

    @Query("SELECT * FROM dokkanCaracters")
    fun getAll(): MutableList<DokkanCaracter>
    //suspend fun getAll(): List<DokkanCaracter>; Le añado 'suspend' si quiere utilizar corrutinas

    @Insert
    fun addPersonaje(personaje: DokkanCaracter)
    //suspend fun addPersonaje(personaje: DokkanCaracter): DokkanCaracter; Le añado 'suspend' si quiere utilizar corrutinas

    @Update
    fun updatePersonaje(personaje: DokkanCaracter)
    //suspend updatePersonaje(personaje: DokkanCaracter): DokkanCaracter; Le añado 'suspend' si quiere utilizar corrutinas

    @Delete
    //fun delete(personaje: DokkanCaracter)
    suspend fun delete(personaje: DokkanCaracter) // Le añado 'suspend' si quiere utilizar corrutinas
}