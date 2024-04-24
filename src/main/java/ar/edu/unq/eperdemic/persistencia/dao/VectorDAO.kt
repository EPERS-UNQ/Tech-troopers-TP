package ar.edu.unq.eperdemic.persistencia.dao

import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.Patogeno
import ar.edu.unq.eperdemic.modelo.RandomGenerator
import ar.edu.unq.eperdemic.modelo.Ubicacion
import ar.edu.unq.eperdemic.modelo.vector.Vector
import org.springframework.data.repository.CrudRepository

interface VectorDAO : CrudRepository<Patogeno, Long> {

    fun crear(vector: Vector): Vector

    fun actualizar(vector: Vector)

    fun recuperar(id: Long?): Vector

    fun recuperarTodos() : List<Vector>

    fun enfermedades(vector: Vector): List<Especie>

    fun recuperarTodosDeUbicacion(ubicacionId: Long): List<Vector>

}