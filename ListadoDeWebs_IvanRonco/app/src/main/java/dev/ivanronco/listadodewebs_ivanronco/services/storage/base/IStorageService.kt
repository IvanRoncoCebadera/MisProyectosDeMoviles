package dev.ivanronco.listadodewebs_ivanronco.services.storage.base

interface IStorageService <T> {
    fun exportData(data: T)
    fun importData(): T
}