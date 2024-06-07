package ar.edu.unq.eperdemic.services

import ar.edu.unq.eperdemic.modelo.Distrito

interface DistritoService {

    fun crear(distrito : Distrito) : Distrito
    fun distritoMasEnfermo() : Distrito
    fun recuperarPorNombre(nombreDistrito: String): Distrito // REVISAR...
    fun actualizarDistrito(distrito: Distrito)
    fun deleteAll()
}