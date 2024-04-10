package ar.edu.unq.eperdemic.services

import ar.edu.unq.eperdemic.modelo.Ubicacion
interface UbicacionService {
    fun crear(ubicacion : Ubicacion)

    fun updatear(ubicacion : Ubicacion)

    fun recuperar(id : Long) : Ubicacion

    fun recuperarTodos() : Collection<Ubicacion>
}