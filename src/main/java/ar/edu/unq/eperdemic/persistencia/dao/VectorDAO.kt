package ar.edu.unq.eperdemic.persistencia.dao

import ar.edu.unq.eperdemic.modelo.Vector

interface VectorDAO {
    fun crear(vector: Vector): Vector

    fun actualizar(vector: Vector)

    fun recuperar(vectorId: Long): Vector

    fun recuperarTodos() : List<Vector>

}