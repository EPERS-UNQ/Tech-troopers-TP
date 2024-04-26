package ar.edu.unq.eperdemic.persistencia.dao

import ar.edu.unq.eperdemic.modelo.Patogeno
import ar.edu.unq.eperdemic.modelo.Ubicacion
import org.springframework.data.repository.CrudRepository

interface UbicacionDAO : CrudRepository<Patogeno, Long> {

    fun crear(ubicacion: Ubicacion): Ubicacion

    fun actualizar(ubicacion: Ubicacion)

    fun recuperar(id: Long?): Ubicacion

    fun recuperarTodos(): List<Ubicacion>

    fun cantidadDeUbicaciones(): Int
    fun recuperarPorNombre(ubicacionId: Long?): String

}