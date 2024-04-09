package ar.edu.unq.eperdemic.services

import ar.edu.unq.eperdemic.modelo.Vector

interface VectorService {

    fun crear(vector: Vector): Vector

    fun updatear(vector: Vector)

    fun recuperar(vectorId: Long): Vector

    fun recuperarTodos(): List<Vector>

}