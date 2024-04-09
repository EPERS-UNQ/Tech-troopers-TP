package ar.edu.unq.eperdemic.persistencia.dao

import ar.edu.unq.eperdemic.modelo.Ubicacion
interface UbicacionDAO {

    fun crear(ubicacion: Ubicacion): Ubicacion

    fun actualizar(ubicacion: Ubicacion)

    fun recuperar(nombre: String): Ubicacion

    fun recuperarTodos() : Collection<Ubicacion>

}