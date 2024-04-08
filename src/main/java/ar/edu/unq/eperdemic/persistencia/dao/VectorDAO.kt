package ar.edu.unq.eperdemic.persistencia.dao

import ar.edu.unq.eperdemic.modelo.Vector

interface VectorDAO {
    fun crear(vector: Vector)

    fun actualizar(vector: Vector)

    fun recuperar(vectorId: Long): Vector

    fun recuperarTodos() : Collection<Vector>

    fun eliminar(vector: Vector)

}