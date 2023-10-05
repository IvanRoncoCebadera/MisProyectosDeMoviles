package dev.ivanronco.listadodewebs_ivanronco.services.storage.paginasWeb

import android.content.Context
import android.net.Uri
import dev.ivanronco.listadodewebs_ivanronco.models.PaginaWeb
import dev.ivanronco.listadodewebs_ivanronco.services.storage.base.IStorageService
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.lang.Exception
import java.net.URL

class PaginasStorageServiceImpl(
    context: Context
): IStorageService<MutableList<PaginaWeb>> {

    private val file = File(context.getExternalFilesDir(null)?.absolutePath!!, "Data")
    private val pyshicalFile = File(file, "WebPages.csv")

    override fun exportData(data: MutableList<PaginaWeb>) {
        if(!file.exists()){
            file.mkdirs()
        }

        BufferedWriter(FileWriter(pyshicalFile)).use {bw ->
            bw.write("id;nombre;url;image;isChecked"+"\n")
            data.forEach {
                bw.append("${it.id};${it.nombre};${it.uri};${it.imagePath};${it.isChecked}"+"\n")
            }
        }
    }

    override fun importData(): MutableList<PaginaWeb> {
        return if (!file.exists()){
            mutableListOf()
        }else {
            try {
                BufferedReader(FileReader(pyshicalFile)).use {br ->
                    br.readLines().drop(1)
                        .map { it.split(";") }.map { it.map { it.trim() } }
                        .map { elementos ->
                            PaginaWeb(
                                id = elementos[0].toLong(),
                                nombre = elementos[1],
                                uri = Uri.parse(elementos[2]),
                                imagePath = elementos[3],
                                isChecked = elementos[4] == "true"
                            )
                        }.toMutableList()
                }
            }catch (e: Exception){
                mutableListOf()
            }
        }
    }
}