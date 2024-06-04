package ar.edu.unq.eperdemic.services

import ar.edu.unq.eperdemic.modelo.UbicacionGlobal
import ar.edu.unq.eperdemic.modelo.UbicacionJpa

interface UbicacionService {

    fun crear(ubicacion : UbicacionGlobal) : UbicacionGlobal

    fun updatear(ubicacion : UbicacionGlobal)

    fun recuperar(id : Long) : UbicacionGlobal

    fun recuperarTodos() : List<UbicacionGlobal>

    fun mover(vectorId: Long, ubicacionId: Long)

    fun expandir(ubicacionId: Long)

    fun conectar(nombreDeUbicacion1:String, nombreDeUbicacion2:String, tipoCamino:String)

    fun conectados(nombreDeUbicacion:String): List<UbicacionGlobal>

    fun moverPorCaminoMasCorto(vectorId:Long, nombreDeUbicacion:String)

    fun deleteAll()

}