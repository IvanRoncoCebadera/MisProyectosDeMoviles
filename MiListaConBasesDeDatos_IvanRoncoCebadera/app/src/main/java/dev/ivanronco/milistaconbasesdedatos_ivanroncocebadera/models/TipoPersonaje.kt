package dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera.models

import dev.ivanronco.milistaconbasesdedatos_ivanroncocebadera.R

enum class TipoPersonaje(val color: Int) {
    STR(R.color.Str), INT(R.color.Int), PHY(R.color.Phy), TEQ(R.color.Teq), AGL(R.color.Agl), DEFAULT(R.color.white)
}