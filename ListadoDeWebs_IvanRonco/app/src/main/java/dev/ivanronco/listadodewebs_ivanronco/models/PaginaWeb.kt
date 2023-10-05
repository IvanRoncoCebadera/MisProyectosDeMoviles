package dev.ivanronco.listadodewebs_ivanronco.models

import android.net.Uri
import java.net.URL

data class PaginaWeb(
    val id: Long = nextId(),
    val nombre: String,
    val uri: Uri,
    val imagePath: String,
    var isChecked: Boolean = false
){
    companion object{
        var contador = 0L
        fun nextId(): Long{
            return contador++
        }
    }
}