package ar.edu.unq.eperdemic.services

import ar.edu.unq.eperdemic.modelo.Ubicacion

interface UbicacionService {

    fun crear(ubicacion : Ubicacion) : Ubicacion

    fun updatear(ubicacion : Ubicacion)

    fun recuperar(id : Long) : Ubicacion

    fun recuperarTodos() : List<Ubicacion>

    fun mover(vectorId: Long, ubicacionId: Long)

    fun expandir(ubicacionId: Long)

    fun conectar(nombreDeUbicacion1:String, nombreDeUbicacion2:String, tipoCamino:String)

    fun conectados(nombreDeUbicacion:String): List<Ubicacion>

    fun moverPorCaminoMasCorto(vectorId:Long, nombreDeUbicacion:String)

}