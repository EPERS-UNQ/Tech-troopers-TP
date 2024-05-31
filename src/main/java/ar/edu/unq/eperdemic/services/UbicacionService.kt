package ar.edu.unq.eperdemic.services

import ar.edu.unq.eperdemic.modelo.UbicacionJpa

interface UbicacionService {

    fun crear(ubicacion : UbicacionJpa) : UbicacionJpa

    fun updatear(ubicacion : UbicacionJpa)

    fun recuperar(id : Long) : UbicacionJpa

    fun recuperarTodos() : List<UbicacionJpa>

    fun mover(vectorId: Long, ubicacionId: Long)

    fun expandir(ubicacionId: Long)

    fun conectar(nombreDeUbicacion1:String, nombreDeUbicacion2:String, tipoCamino:String,largo: Int)

    fun conectados(nombreDeUbicacion:String): List<UbicacionJpa>

    fun moverPorCaminoMasCorto(vectorId:Long, nombreDeUbicacion:String)

    fun deleteAll()

}