package dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera.config

import java.io.FileNotFoundException
import java.util.*

class ConfigApp {
    val APP_DATABASE_URL: String by lazy {
        readProperty("app.database.url", "")
    }

    val APP_DATABASE_INIT: Boolean by lazy {
        readProperty("app.database.initData", "false") == "true"
    }

    private fun readProperty(property: String, defaultValue: String): String{
        val properties = Properties()
        properties.load(
            ClassLoader.getSystemResourceAsStream("application.properties") ?: throw FileNotFoundException("El fichero de propiedades no se ha encontrado.")
        )
        return properties.getProperty(property) ?: defaultValue
    }
}