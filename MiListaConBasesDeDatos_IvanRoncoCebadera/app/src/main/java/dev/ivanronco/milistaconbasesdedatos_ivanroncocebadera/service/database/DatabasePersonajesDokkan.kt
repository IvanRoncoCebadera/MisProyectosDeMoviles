package dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera.service.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera.config.ConfigApp
import dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera.models.DokkanCaracter
import dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera.models.TipoPersonajeConverter

@Database(entities = [DokkanCaracter::class], version = 1)
@TypeConverters(TipoPersonajeConverter::class)
abstract class DatabasePersonajesDokkan: RoomDatabase() {
    abstract fun dokkanCaracterDao(): DokkanCaracterDao
}