package dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera.service.database

import android.app.Application
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DokkanCaracterApplication: Application() {

    companion object{
        lateinit var database: DatabasePersonajesDokkan
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            applicationContext,
            DatabasePersonajesDokkan::class.java,
            "dokkanCaractersDb"
        ).build()
    }

}