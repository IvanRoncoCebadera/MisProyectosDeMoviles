package dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera.R
import java.io.Serializable

@Entity(tableName = "dokkanCaracters")
data class DokkanCaracter(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nombre: String = "",
    val tipoPersonaje: TipoPersonaje = TipoPersonaje.DEFAULT,
    var esFavorito: Boolean = false,
    val imagen: String = ""
): Serializable

@TypeConverters(TipoPersonajeConverter::class)
enum class TipoPersonaje(val color: Int) {
    STR(R.color.Str), INT(R.color.Int), PHY(R.color.Phy), TEQ(R.color.Teq), AGL(R.color.Agl), DEFAULT(R.color.white)
}

class TipoPersonajeConverter {
    @TypeConverter
    fun toEnum(value: String) = enumValueOf<TipoPersonaje>(value)

    @TypeConverter
    fun toString(value: TipoPersonaje) = value.name
}
